
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:rumah_sehat_mobile/appointment/screen/detail_appointment.dart';

Widget appointmentTemplate(appointment, context) {
  return Card(
    shadowColor: Colors.black38,
    margin: const EdgeInsets.fromLTRB(16.0, 8.0, 16.0, 8.0),
    child: Padding(
      padding: const EdgeInsets.all(15.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          SizedBox(height: 6.0),
          Text(
            "Nama Dokter: " + appointment.namaDokter,
            style: TextStyle(
              fontSize: 30.0,
              color: Colors.grey[800],
              fontWeight: FontWeight.bold,
            ),
          ),
          SizedBox(height: 6.0),
          Text(
            "Waktu Awal Appointment: " + DateFormat('dd-MM-yyyy HH:mm').format(appointment.waktuAwal),
            style: TextStyle(
              fontSize: 14.0,
              color: Colors.grey[500],
            ),
          ),
          SizedBox(height: 15.0),
          Text(
            "Status Appointment: " + (appointment.isDone? "Selesai":"Belum selesai"),
            style: TextStyle(
              fontSize: 20.0,
              color: appointment.isDone? Colors.green[800] : Colors.red[800],
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
                MaterialPageRoute(builder: (context) => detailAppointment(),
                settings: RouteSettings(
                  arguments: appointment
                )
                ),
              );
            },
            child: Text('Detail Appointment'),
          )
        ],
      ),
    ),
  );
}