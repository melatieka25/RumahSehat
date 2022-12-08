import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'dart:ui';
import 'package:rumah_sehat_mobile/appointment/model/appointment.dart';
import 'package:rumah_sehat_mobile/resep/screen/detail_resep.dart';
import '../../main.dart';

class detailAppointment extends StatelessWidget {
  const detailAppointment({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    final appointment =
        ModalRoute.of(context)!.settings.arguments as Appointment;
    return Scaffold(
        appBar: AppBar(
          title: Text("RumahSehat"),
          backgroundColor: Colors.blueGrey,
        ),
        body: Column(children: <Widget>[
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
                  "Appointment",
                  style: TextStyle(
                      fontSize: 30,
                      color: Colors.blue,
                      fontWeight: FontWeight.w700),
                ),
              ])),
          Padding(
            padding: EdgeInsets.symmetric(vertical: 10, horizontal: 75),
            child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: <Widget>[
                  SizedBox(height: 6.0),
                  Text(
                    "Kode Appointment: " + appointment.kode.toString(),
                    style: TextStyle(
                      fontSize: 20.0,
                      color: Colors.grey[800],
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 6.0),
                  Text(
                    "Waktu Awal Appointment: " +
                        DateFormat('dd-MM-yyyy HH:mm')
                            .format(appointment.waktuAwal),
                    style: TextStyle(
                      fontSize: 14.0,
                      color: Colors.grey[500],
                    ),
                  ),
                  SizedBox(height: 15.0),
                  Text(
                    "Status Appointment: " +
                        (appointment.isDone ? "Selesai" : "Belum selesai"),
                    style: TextStyle(
                      fontSize: 20.0,
                      color: appointment.isDone
                          ? Colors.green[800]
                          : Colors.red[800],
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 6.0),
                  Text(
                    "Nama Dokter: " + appointment.namaDokter.toString(),
                    style: TextStyle(
                      fontSize: 20.0,
                      color: Colors.grey[800],
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 6.0),
                  Text(
                    "Nama Pasien: " + appointment.namaPasien.toString(),
                    style: TextStyle(
                      fontSize: 20.0,
                      color: Colors.grey[800],
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  if (appointment.resep != null)
                    OutlinedButton(
                      style: TextButton.styleFrom(
                        foregroundColor: Colors.blue,
                        disabledForegroundColor: Colors.red,
                      ),
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => DetailResepScreen(),
                              settings: RouteSettings(
                                  arguments: appointment.resep?.id)),
                        );
                      },
                      child: Text('Detail Resep'),
                    )
                ]),
          ),
        ]));
  }
}
