import 'dart:io';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:rumah_sehat_mobile/appointment/model/appointment.dart';
import 'package:rumah_sehat_mobile/login/login_page.dart';
import 'package:rumah_sehat_mobile/resep/widget/obat_template.dart';
import 'dart:async';

import '../../widget/drawer.dart';
import '../model/resep.dart';

class DetailResepScreen extends StatelessWidget {
  const DetailResepScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final id_resep = ModalRoute.of(context)!.settings.arguments as int;
    Future<Resep> _fetchData() async {
      var url =
          Uri.parse('http://10.0.2.2:8081/api/v1/resep/' + id_resep.toString());
      // TODO HAPUS
      print(LoginPage.token);
      var response = await http.get(url, headers: {
        "Access-Control_Allow_Origin": "*",
        HttpHeaders.authorizationHeader: "Bearer " + LoginPage.token
      });
      Resep resep = ResepFromJson(response.body);
      return resep;
    }

    return Scaffold(
        appBar: AppBar(
          title: Text("RumahSehat"),
          backgroundColor: Colors.blueGrey,
        ),
        body: SingleChildScrollView(
          child: Column(children: <Widget>[
            const SizedBox(height: 20),
            Padding(
              padding: EdgeInsets.symmetric(vertical: 10, horizontal: 75),
              child: Row(children: [
                const Text(
                  "Detail ",
                  style: TextStyle(
                      fontSize: 30,
                      color: Colors.lightGreen,
                      fontWeight: FontWeight.w700),
                ),
                const Text(
                  "Resep",
                  style: TextStyle(
                      fontSize: 30,
                      color: Colors.blue,
                      fontWeight: FontWeight.w700),
                ),
              ]),
            ),
            Padding(
              padding: EdgeInsets.symmetric(vertical: 10, horizontal: 75),
              child: FutureBuilder<Resep>(
                  future: _fetchData(),
                  builder: (BuildContext context, AsyncSnapshot snapshot) {
                    if (snapshot.hasError) {
                      print("error");
                      print(snapshot.stackTrace);
                    }
                    if (snapshot.hasData) {
                      Resep resep = snapshot.data;
                      return Column(
                          crossAxisAlignment: CrossAxisAlignment.stretch,
                          children: <Widget>[
                            SizedBox(height: 6.0),
                            Text(
                              "ID Resep: " + resep.id.toString(),
                              style: TextStyle(
                                fontSize: 20.0,
                                color: Colors.grey[800],
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            SizedBox(height: 6.0),
                            Text(
                              "Nama Dokter: " + resep.namaDokter.toString(),
                              style: TextStyle(
                                fontSize: 20.0,
                                color: Colors.grey[800],
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            SizedBox(height: 6.0),
                            Text(
                              "Nama Pasien: " + resep.namaPasien.toString(),
                              style: TextStyle(
                                fontSize: 20.0,
                                color: Colors.grey[800],
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            SizedBox(height: 15.0),
                            Text(
                              "Status Resep: " +
                                  (resep.isDone ? "Selesai" : "Belum selesai"),
                              style: TextStyle(
                                fontSize: 20.0,
                                color: resep.isDone
                                    ? Colors.green[800]
                                    : Colors.red[800],
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            SizedBox(height: 6.0),
                            if (resep.namaApoteker != "null")
                              Text(
                                "Nama Apoteker: " +
                                    resep.namaApoteker.toString(),
                                style: TextStyle(
                                  fontSize: 20.0,
                                  color: Colors.grey[800],
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                            SizedBox(height: 30.0),
                            Text(
                              "Daftar Obat",
                              style: TextStyle(
                                fontSize: 24.0,
                                color: Colors.grey[800],
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            Column(
                              children: [
                                for (int x = 0;
                                    x < resep.listJumlah.length;
                                    x++)
                                  obatTemplate(
                                      resep.listJumlah[x]["namaObat"]
                                          .toString(),
                                      resep.listJumlah[x]["kuantitas"]
                                          .toString(),
                                      context),
                              ],
                            )
                          ]);
                    } else {
                      return Center(
                        child: Column(
                          children: const [
                            SizedBox(height: 100),
                            Text("Sedang mengambil data.."),
                            SizedBox(height: 20),
                            SizedBox(
                                width: 30,
                                height: 30,
                                child: CircularProgressIndicator()),
                          ],
                        ),
                      );
                    }
                  }),
            )
          ]),
        ));
  }
}
