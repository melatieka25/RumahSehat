import 'dart:convert';

import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:rumah_sehat_mobile/registrasi_pasien/screen/form_registrasi_pasien.dart';
import 'package:rumah_sehat_mobile/widget/drawer.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:rumah_sehat_mobile/login/login_page.dart';

import '../main.dart';

class TopupSaldoPage extends StatelessWidget {
  const TopupSaldoPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
          brightness: Brightness.light,
          primaryColor: Colors.lightBlue[800],

          fontFamily: 'Fredoka One',

          textTheme: const TextTheme(
            headline6: TextStyle(fontSize: 24.0),
          )
      ),
      home: Scaffold(
        body: Container(
          child: MyCardWidget(),
        ),
      ),
    );
  }
}
class MyCardWidget extends StatefulWidget {
  const MyCardWidget({Key? key}) : super(key: key);

  @override
  MyCardWidgetState createState() {
    return MyCardWidgetState();
  }
}

class MyCardWidgetState extends State<MyCardWidget> {
  final textController = TextEditingController();

  final ScrollController _firstController = ScrollController();
  String query = "";

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("RumahSehat"),
          backgroundColor: Colors.blueGrey,
        ),
        backgroundColor: Colors.white,
        //https://api.flutter.dev/flutter/material/FloatingActionButton-class.html
        drawer: const MyDrawer(),
        body: Column(
            children: <Widget>[
              const SizedBox(height: 20),
              Padding(
                padding: const EdgeInsets.only(top: 10),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: const [
                      Text( "Topup Saldo", style: TextStyle(
                          fontSize: 30,
                          color: Colors.lightGreen,
                          fontWeight: FontWeight.w700),
                      ),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 15.0, horizontal: 25.0),
                child: TextField(
                  controller: textController,
                  decoration: const InputDecoration(
                    labelText: "Jumlah (Rp)",
                    hintText: 'Jumlah yang ingin ditop-up',
                  ),
                  keyboardType: TextInputType.number,
                  inputFormatters: <TextInputFormatter>[
                    FilteringTextInputFormatter.digitsOnly
                  ],
                ),
              ),
              OutlinedButton(
                style: TextButton.styleFrom(
                  foregroundColor: Colors.blue,
                  disabledForegroundColor: Colors.red,
                ),
                onPressed: () => showDialog<String>(
                  context: context,
                  builder: (BuildContext _context) => AlertDialog(
                    title: const Text('Konfirmasi Topup'),
                    content: Text('Apakah Anda yakin akan melakukan topup sebesar Rp' + textController.text + '?'),
                    actions: <Widget>[
                      TextButton(
                        onPressed: () => Navigator.pop(_context),
                        child: const Text('Cancel'),
                      ),
                      TextButton(
                        onPressed: () async {
                            print(LoginPage.token);
                            print(jsonEncode(<String, String>{
                              'username': LoginPage.username,
                              'saldo': textController.text,
                            }));
                            final response = await http.post(
                              Uri.parse(
                                  "http://10.0.2.2:8081/api/v1/pasien/saldo"),
                              headers: <String, String>{
                                "Content-Type": "application/json;charset=UTF-8",
                                "Authorization": "Bearer " + LoginPage.token,
                              },
                              body: jsonEncode(<String, String>{
                                'username': LoginPage.username,
                                'saldo': textController.text,
                              }),
                            );
                            if (response.statusCode == 200) {
                              Navigator.pop(_context);
                              showDialog<String>(
                                context: context,
                                builder: (BuildContext _context) => AlertDialog(
                                  title: const Text('Konfirmasi Topup'),
                                  content: Text('Saldo telah berhasil ditop-up sebesar Rp' + textController.text + ''),
                                  actions: <Widget>[
                                    TextButton(
                                      onPressed: () async {
                                        Navigator.pop(_context);
                                      },
                                      child: const Text('OK'),
                                    ),
                                  ],
                                ),
                              );
                              Navigator.push(
                                  context, MaterialPageRoute(builder: (context) => const HomePage(title: 'RumahSehat')));
                            } else {
                              Navigator.pop(_context);
                              ScaffoldMessenger.of(context).showSnackBar(
                                  const SnackBar(content: Text("Terjadi masalah dalam proses update saldo.")));
                            }
                        },
                        child: const Text('OK'),
                      ),
                    ],
                  ),
                ),
                child: Text('Topup'),
              ),
            ]
        )
    );
  }
}