import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:ui';
import 'package:rumah_sehat_mobile/appointment/model/appointment.dart';

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

class AppointmentForm extends StatefulWidget {
  const AppointmentForm({Key? key}) : super(key: key);

  @override
  AppointmentFormState createState() {
    return AppointmentFormState();
  }
}

var _controllerNama = TextEditingController();
var _controllerUsername = TextEditingController();
var _controllerPassword = TextEditingController();
var _controllerEmail = TextEditingController();
var _controllerUmur = TextEditingController();

class AppointmentFormState extends State<AppointmentForm> {
  String finalResponse = "";
  final _formKey = GlobalKey<FormState>();

  _showDialog(BuildContext context) {

    VoidCallback continueCallBack = () => {
      Navigator.of(context).pop(),
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
                const Text('Tes'),
                DatePickerDialog(
                    firstDate: DateTime(2000),
                    lastDate: DateTime(2099),
                    initialDate: DateTime.now(),
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
                    return 'validateEmail(value)';
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
                    return '';
                    // return validateUsername(value);
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
                    // String? valResult = validatePassword(value);
                    // if (valResult != null){
                    //   return valResult;
                    // }
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
                    //_savingData();
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