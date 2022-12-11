import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:ui';
import 'package:rumah_sehat_mobile/registrasi_pasien/model/pasien.dart';
import 'package:rumah_sehat_mobile/saldo/topup_saldo.dart';

import '../../login/login_page.dart';
import '../../widget/drawer.dart';

class profilPasien extends StatefulWidget {
  const profilPasien({Key? key}) : super(key: key);

  @override
  profilPasienState createState() {
    return profilPasienState();
  }
}

class profilPasienState extends State<profilPasien> {
  Future<Pasien> _fetchData() async {
    var url = Uri.parse('https://apap-090.cs.ui.ac.id/api/v1/pasien/profil/' +
        LoginPage.username);
    var response = await http.get(url, headers: {
      "Access-Control_Allow_Origin": "*",
      "Authorization": "Bearer " + LoginPage.token
    });
    print(response.body);
    //var data = jsonDecode(response.body);
    Pasien pasien = PasienFromJson(response.body);
    setState(() {
      _pasien = pasien;
    });
    return pasien;
  }

  final ScrollController _firstController = ScrollController();
  String query = "";
  Pasien _pasien = new Pasien(
      nama: "",
      role: "",
      username: "",
      password: "",
      email: "",
      saldo: 0,
      umur: 0,
      isSso: false);

  // Sumber: https://github.com/JohannesMilke/filter_listview_example
  @override
  void initState() {
    super.initState();
    _fetchData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("RumahSehat"),
          backgroundColor: Colors.blueGrey,
        ),
        backgroundColor: Colors.white,
        drawer: const MyDrawer(),
        body: Column(children: <Widget>[
          const SizedBox(height: 20),
          Padding(
            padding: EdgeInsets.symmetric(vertical: 10, horizontal: 115),
            child: Row(
              children: [
                const Text(
                  "Profil ",
                  style: TextStyle(
                      fontSize: 30,
                      color: Colors.lightGreen,
                      fontWeight: FontWeight.w700),
                ),
                const Text(
                  "Pasien",
                  style: TextStyle(
                      fontSize: 30,
                      color: Colors.blue,
                      fontWeight: FontWeight.w700),
                ),
              ],
            ),
          ),
          Expanded(
              child: Padding(
                  padding: const EdgeInsets.all(10),
                  child: Column(children: [
                    SizedBox(height: 6.0),
                    Text(
                      "Username: " + _pasien.username,
                      style: TextStyle(
                        fontSize: 20.0,
                        color: Colors.grey[800],
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: 6.0),
                    Text(
                      "Email: " + _pasien.email,
                      style: TextStyle(
                        fontSize: 20.0,
                        color: Colors.grey[800],
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: 6.0),
                    Text(
                      "Nama: " + _pasien.nama,
                      style: TextStyle(
                        fontSize: 20.0,
                        color: Colors.grey[800],
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: 6.0),
                    Text(
                      "Saldo: " + _pasien.saldo.toString(),
                      style: TextStyle(
                        fontSize: 20.0,
                        color: Colors.grey[800],
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    OutlinedButton(
                      style: TextButton.styleFrom(
                        foregroundColor: Colors.blue,
                        disabledForegroundColor: Colors.red,
                      ),
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => TopupSaldoPage()),
                        );
                      },
                      child: Text('Topup Saldo'),
                    ),
                  ])))
        ]));
  }
}
