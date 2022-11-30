
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:rumah_sehat_mobile/main.dart';

Widget tagihanTemplate(tagihan, context) {
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
            "Nomor: " + tagihan.kode,
            style: TextStyle(
              fontSize: 30.0,
              color: Colors.grey[800],
              fontWeight: FontWeight.bold,
            ),
          ),
          SizedBox(height: 6.0),
          Text(
            "Tanggal terbuat: " + tagihan.tanggalTerbuat.substring(0, 10),
            style: TextStyle(
              fontSize: 14.0,
              color: Colors.grey[500],
            ),
          ),
          SizedBox(height: 15.0),
          Text(
            "Status: " + (tagihan.isPaid? "Lunas":"Belum dibayar"),
            style: TextStyle(
              fontSize: 20.0,
              color: tagihan.isPaid? Colors.green[800] : Colors.red[800],
              fontWeight: FontWeight.bold,
            ),
          ),
          OutlinedButton(
            style: TextButton.styleFrom(
              foregroundColor: Colors.blue,
              disabledForegroundColor: Colors.red,
            ),
            // TODO: Navigasi ke detail tagihan masukin disini
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => HomePage(title: "RumahSehat")),
              );
            },
            child: Text('Detail Tagihan'),
          )
        ],
      ),
    ),
  );
}