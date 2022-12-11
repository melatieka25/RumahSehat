import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:rumah_sehat_mobile/login/login_page.dart';
import 'dart:ui';
import 'package:rumah_sehat_mobile/registrasi_pasien/model/pasien.dart';

import '../../main.dart';

// https://docs.flutter.dev/cookbook/forms/validation
class Dialog extends StatelessWidget {

  String title;
  String content;
  VoidCallback continueCallBack;
  int statusCode;

  Dialog(this.title, this.content, this.continueCallBack, this.statusCode);
  TextStyle textStyle = TextStyle (color: Colors.black);

  @override
  Widget build(BuildContext context) {
    return BackdropFilter(
        filter: ImageFilter.blur(sigmaX: 6, sigmaY: 6),
        child: AlertDialog(
          title: new Text(title, style: textStyle,),
          content: new Text(content, style: textStyle,),
          actions: <Widget>[
            TextButton(
              child: Text("Kembali"),
              onPressed: () {
                if (statusCode == 200) {
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) =>
                      const HomePage(
                          title: "RumahSehat")));
                } else {
                  Navigator.of(context).pop();
                }
              },
            ),
          ],
        ));
  }
}

// Define a custom Form widget.
class PasienForm extends StatefulWidget {
  const PasienForm({Key? key}) : super(key: key);

  @override
  PasienFormState createState() {
    return PasienFormState();
  }
}

var _controllerNama = TextEditingController();
var _controllerUsername = TextEditingController();
var _controllerPassword = TextEditingController();
var _controllerEmail = TextEditingController();
var _controllerUmur = TextEditingController();

// Define a corresponding State class.
// This class holds data related to the form.
class PasienFormState extends State<PasienForm> {
  String finalResponse = "";
  final _formKey = GlobalKey<FormState>();

  _showDialog(BuildContext context, int statusCode, String reason) {

    VoidCallback continueCallBack = () => {
      Navigator.of(context).pop(),
      // code on continue comes here
    };
    Dialog alert = Dialog("", "", continueCallBack, statusCode);
    if (statusCode == 200) {
      alert = Dialog("Success!", "Akun berhasil dibuat!" ,continueCallBack, statusCode);
    } else {
      if (reason.contains("email")){
        alert = Dialog("Gagal!", "Akun dengan email yang sama sudah pernah dibuat!",continueCallBack, statusCode);
      } else if (reason.contains("username")){
        alert = Dialog("Gagal!", "Akun dengan username yang sama sudah pernah dibuat!",continueCallBack, statusCode);
      } else {
        alert = Dialog(
            "Gagal!", "Akun gagal dibuat!", continueCallBack, statusCode);
      }
    }


    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }

  Future<Pasien>? createPasien(String nama, String role, String username, String password, String email, int saldo, int umur) async {

    Pasien newPasien = Pasien(nama: nama, role: role, username: username, password: password, email: email, saldo: saldo, umur: umur, isSso: false);

    final response = await http.post(
      //Uri.parse('https://apap-090.cs.ui.ac.id/api/v1/pasien/new'),
      Uri.parse('http://10.0.2.2:8081/api/v1/pasien/new'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: PasienToJson(newPasien),
    );

    final tokenResponse = await http.post(
      Uri.parse(
          "http://10.0.2.2:8081/api/v1/authenticate"),
        //"https://apap-090.cs.ui.ac.id/api/v1/authenticate"),
      headers: <String, String>{
        "Content-Type": "application/json;charset=UTF-8",
      },
      body: jsonEncode(<String, String>{
        'username': _controllerUsername.text,
        'password': _controllerPassword.text,
      }),
    );

    if (response.statusCode == 200 && tokenResponse.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      _showDialog(context, response.statusCode, response.body);

      setState(() {
        LoginPage.token = jsonDecode(tokenResponse.body)['token'];
        LoginPage.roles = "Pasien";
        LoginPage.username =  _controllerUsername.text;
      });

      _controllerNama.clear();
      _controllerUsername.clear();
      _controllerPassword.clear();
      _controllerEmail.clear();
      _controllerUmur.clear();

      FocusScope.of(context).unfocus();
      Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => const HomePage(title: "RumahSehat")));

      return Pasien.fromJson(jsonDecode(response.body));
    } else {
      // If the server did not return a 200 CREATED response,
      // then throw an exception.
      print(response.body);
      _showDialog(context, response.statusCode, response.body);
      throw Exception('Pembuatan akun gagal');
    }
  }

  Future<void> _savingData() async{

    final validation = _formKey.currentState!.validate();

    if (!validation){
      return;
    }

    _formKey.currentState!.save();

    try {
      createPasien(
          _controllerNama.text,
          "Pasien",
          _controllerUsername.text,
          _controllerPassword.text,
          _controllerEmail.text,
          0,
          int.parse(_controllerUmur.text));
    } catch (exception){
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Maaf, pembuatan akun gagal")));
    }
  }

  //Referensi: https://stackoverflow.com/questions/56253787/how-to-handle-textfield-validation-in-password-in-flutter
  String? validatePassword(String value) {
    RegExp regex =
    RegExp(r'^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#\$&*~]).{8,}$');

    if (!regex.hasMatch(value)) {
      return 'Password tidak valid!';
    } else {
      return null;
    }
  }

  final ScrollController _firstController = ScrollController();

  @override
  Widget build(BuildContext context) {
    // Build a Form widget using the _formKey created above.
    return Scaffold(
      appBar: AppBar(
        title: Text('Registrasi Pasien'),
        backgroundColor: Colors.blueGrey,
      ),
      body: Container(
        padding: EdgeInsets.all(20.0),
        child: Form(
          key: _formKey,
          child: Scrollbar(
            isAlwaysShown: true,
            controller: _firstController,
            child:
            ListView(
              controller: _firstController,
              children: [
                SizedBox(height: 30),
                const Text('Nama'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  onSaved: (value) {
                  },
                  controller: _controllerNama,
                  // The validator receives the text that the user has entered.
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan Nama!';
                    }
                    return null;
                  },
                ),
                SizedBox(height: 30),
                const Text('Email'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  controller: _controllerEmail,
                  // The validator receives the text that the user has entered.
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan Email!';
                    }
                  },
                ),
                SizedBox(height: 30),
                const Text('Username'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  controller: _controllerUsername,
                  // The validator receives the text that the user has entered.
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan Username!';
                    }
                  },
                  onSaved: (value) {
                    // price = int.parse(value!);
                  },
                ),
                SizedBox(height: 30),
                const Text('Password'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  obscureText: true,
                  enableSuggestions: false,
                  autocorrect: false,
                  controller: _controllerPassword,
                  // The validator receives the text that the user has entered.
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan Password!';
                    }
                    String? valResult = validatePassword(value);
                    if (valResult != null){
                      return valResult;
                    }
                    return null;
                  },
                  onSaved: (value) {
                  },
                ),
                SizedBox(height: 30),
                const Text('Umur'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  controller: _controllerUmur,
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan Umur!';
                    }
                    return null;
                  },
                  onSaved: (value) {
                  },
                  // The validator receives the text that the user has entered.
                ),
                  // The validator receives the text that the user has entered.
                SizedBox(height: 40),
                ElevatedButton(
                  onPressed: () async {
                    _savingData();
                  },
                  child: const Text('Register'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}