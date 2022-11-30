import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:rumah_sehat_mobile/login/login_page.dart';
import 'package:rumah_sehat_mobile/main.dart';
import 'dart:ui';
import 'package:rumah_sehat_mobile/registrasi_pasien/model/pasien.dart';

// https://docs.flutter.dev/cookbook/forms/validation
class Dialog extends StatelessWidget {

  String title;
  String content;
  VoidCallback continueCallBack;

  Dialog(this.title, this.content, this.continueCallBack);
  TextStyle textStyle = TextStyle (color: Colors.black);

  @override
  Widget build(BuildContext context) {
    return BackdropFilter(
        filter: ImageFilter.blur(sigmaX: 6, sigmaY: 6),
        child:  AlertDialog(
          title: new Text(title,style: textStyle,),
          content: new Text(content, style: textStyle,),
          actions: <Widget>[
            TextButton(
              child: Text("Kembali"),
              onPressed: () {
                continueCallBack();
                Navigator.of(context).pop();
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

  _showDialog(BuildContext context) {

    VoidCallback continueCallBack = () => {
      //Navigator.of(context).pop(),
      // code on continue comes here
    };
    Dialog alert = Dialog("Hore akun tersimpan!", "Selamat datang " + _controllerNama.text + "!",continueCallBack);


    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }

  Future<Pasien> createPasien(String nama, String role, String username, String password, String email, int saldo, int umur) async {

    Pasien newPasien = Pasien(nama: nama, role: role, username: username, password: password, email: email, saldo: saldo, umur: umur, isSso: false);
    print(PasienToJson(newPasien));

    final response = await http.post(
      Uri.parse('http://10.0.2.2:8081/api/v1/pasien/new'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: PasienToJson(newPasien)
      ,
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      _showDialog(context);

      _controllerNama.clear();
      _controllerUsername.clear();
      _controllerPassword.clear();
      _controllerEmail.clear();
      _controllerUmur.clear();

      FocusScope.of(context).unfocus();

      return Pasien.fromJson(jsonDecode(response.body));
    } else {
      print(response.body);
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      throw Exception('Pembuatan akun gagal');
    }
  }
  Future<void> _savingData() async{

    final validation = _formKey.currentState!.validate();

    if (!validation){
      return;
    }

    _formKey.currentState!.save();

    createPasien(_controllerNama.text, "Pasien", _controllerUsername.text,
        _controllerPassword.text, _controllerEmail.text, 0,
        int.parse(_controllerUmur.text));

    setState(() {
      LoginPage.roles = "Pasien";
      LoginPage.username =  _controllerUsername.text;
    });
    Navigator.of(context).pushNamed("home");

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

  var responseEmail;
  var responseUsername;

  Future<String?> getEmail() async {
    var url = Uri.parse(
        'http://10.0.2.2:8081/api/v1/user/email');
    var response =
        await http.get(url, headers: {"Access-Control_Allow_Origin": "*"});
    setState(() {
      responseEmail = response.body;
    });
  }

  Future<String?> getUsername() async {
    var url = Uri.parse(
        'http://10.0.2.2:8081/api/v1/user/username');
    var response =
    await http.get(url, headers: {"Access-Control_Allow_Origin": "*"});
    setState(() {
      responseUsername = response.body;
    });
  }


  String? validateEmail(String value) {

    if (responseEmail.contains(value)) {
      return "Email sudah digunakan!";
    } else {
      return null;
    }
  }

  String? validateUsername(String value) {

    if (responseUsername.contains(value)) {
      return "Username sudah digunakan!";
    } else {
      return null;
    }
  }

  final ScrollController _firstController = ScrollController();

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    print("initState");
    WidgetsBinding.instance!.addPostFrameCallback((_) {
      print("WidgetsBinding");
      getEmail();
      getUsername();
    });
  }

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
                    return validateEmail(value);
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

                    return validateUsername(value);
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
                    //testImage = value!;
                  },
                ),
                SizedBox(height: 30),
                const Text('Umur'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  // onSaved: (value) {
                  //   title = value!;
                  // },
                  controller: _controllerUmur,
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan Umur!';
                    }
                    return null;
                  },
                  onSaved: (value) {
                    //description = value!;
                  },
                  // The validator receives the text that the user has entered.
                ),
                  // The validator receives the text that the user has entered.
                SizedBox(height: 40),
                ElevatedButton(
                  onPressed: () async {
                    // createTest("tes covid", "https://fajar.co.id/wp-content/uploads/img/no-image.jpg", "PCR", "Bekasi", 200000,
                    //     "jakarta", "tidak ada", "https://fajar.co.id/wp-content/uploads/img/no-image.jpg", "5 jam", "08:00", "0888888", "mitra@gmail.com");
                    // Validate returns true if the form is valid, or false otherwise.
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