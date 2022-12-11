
import 'package:flutter/material.dart';

Widget obatTemplate(namaObat, kuantitas, context) {
  return Card(
    color: Colors.lightBlue[100],
    shadowColor: Colors.black38,
    margin: const EdgeInsets.fromLTRB(0.0, 8.0, 0.0, 8.0),
    child: Padding(
      padding: const EdgeInsets.all(15.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          const SizedBox(height: 6.0),
          Text(
            namaObat,
            style: TextStyle(
              fontSize: 20.0,
              color: Colors.grey[800],
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(height: 6.0),
          Text(
            "Jumlah: " + kuantitas,
            style: TextStyle(
              fontSize: 16.0,
              color: Colors.grey[800],
            ),
          ),
        ],
      ),
    ),
  );
}
