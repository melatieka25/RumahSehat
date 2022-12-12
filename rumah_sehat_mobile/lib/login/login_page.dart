import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:rumah_sehat_mobile/registrasi_pasien/screen/form_registrasi_pasien.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../main.dart';

class LoginPage extends StatefulWidget {
  const LoginPage();
  static String roles = "";
  static String username = "";
  static String token = "";
  @override
  _LoginPageState createState() => new _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final GlobalKey<FormState> _loginFormKey = GlobalKey<FormState>();

  bool isVisible = false;

  void toggleVisibility() {
    setState(() {
      isVisible = !isVisible;
    });
  }

  String username = "";
  String password1 = "";
  String role = "";

  @override
  Widget build(BuildContext context) {
    // ignore: unused_local_variable

    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Padding(
          padding: EdgeInsets.fromLTRB(24.0, 40.0, 24.0, 0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Hero(
                tag: 'RumahSehat',
                child: CircleAvatar(
                  backgroundColor: Colors.transparent,
                  radius: 84.0,
                  child: Image.asset('assets/images/logo.jpeg'),
                ),
              ),
              Form(
                key: _loginFormKey,
                child: Column(children: [
                  //Username
                  Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                    ),
                    child: TextFormField(
                      onChanged: (String value) {
                        username = value;
                      },
                      decoration: InputDecoration(
                        hintText: 'Username',
                        contentPadding:
                            EdgeInsets.fromLTRB(20.0, 10.0, 20.0, 10.0),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(32.0)),
                      ),
                      autovalidateMode: AutovalidateMode.onUserInteraction,
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return "Username Harus di Isi";
                        } else {
                          return null;
                        }
                      },
                    ),
                  ),
                  SizedBox(
                    height: 15,
                  ),

                  //Password
                  Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                    ),
                    child: TextFormField(
                      onChanged: (String value) {
                        password1 = value;
                      },
                      decoration: InputDecoration(
                        hintText: 'Password',
                        contentPadding:
                            EdgeInsets.fromLTRB(20.0, 10.0, 20.0, 10.0),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(32.0)),
                        suffixIcon: IconButton(
                          onPressed: toggleVisibility,
                          icon: Icon(isVisible
                              ? Icons.visibility_outlined
                              : Icons.visibility_off_outlined),
                        ),
                      ),
                      obscureText: !isVisible,
                      autovalidateMode: AutovalidateMode.onUserInteraction,
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return "Password Harus di Isi";
                        } else {
                          return null;
                        }
                      },
                    ),
                  ),
                ]),
              ),
              SizedBox(
                height: 20,
              ),
              Container(
                child: ElevatedButton(
                    onPressed: () async {
                      print(username);
                      print(password1);
                      if (_loginFormKey.currentState!.validate()) {
                        final response = await http.post(
                          Uri.parse(
                              "https://apap-090.cs.ui.ac.id/api/v1/authenticate"),
                          headers: <String, String>{
                            "Content-Type": "application/json;charset=UTF-8",
                          },
                          body: jsonEncode(<String, String>{
                            'username': username,
                            'password': password1,
                          }),
                        );

                        if (response.statusCode == 200) {
                          setState(() {
                            LoginPage.roles = "Pasien";
                            LoginPage.username = username;
                            LoginPage.token =
                                jsonDecode(response.body)['token'];
                          });
                          //pindah halaman
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) =>
                                      const HomePage(title: "RumahSehat")));
                        } else {
                          ScaffoldMessenger.of(context).showSnackBar(
                              const SnackBar(
                                  content: Text(
                                      "Username dan password tidak valid")));
                        }
                      }
                    },
                    child:
                        Text('Log In', style: TextStyle(color: Colors.white)),
                    style: ElevatedButton.styleFrom(
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(24),
                      ),
                      padding: EdgeInsets.all(12),
                      primary: Colors.green,
                    )),
              ),
              SizedBox(
                height: 15,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    "Doesn't have an Account?",
                    style: TextStyle(color: Colors.black54),
                  ),
                  GestureDetector(
                    onTap: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => PasienForm()));
                    },
                    child: Text(
                      "Register",
                      style: TextStyle(color: Colors.green),
                    ),
                  )
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
