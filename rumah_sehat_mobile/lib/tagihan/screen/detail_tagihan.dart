import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:rumah_sehat_mobile/tagihan/model/tagihan.dart';
import 'package:http/http.dart' as http;
import 'package:rumah_sehat_mobile/login/login_page.dart';

import '../../main.dart';

// ignore: camel_case_types
class detailTagihan extends StatelessWidget {
  const detailTagihan({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final tagihan = ModalRoute.of(context)!.settings.arguments as Tagihan;
    final formatCurrency = NumberFormat("#,##0.00");

    return Scaffold(
        appBar: AppBar(
          title:  Image.asset('assets/images/long-logo.png', width: 200),
          backgroundColor: Colors.white,
          iconTheme: IconThemeData(color: Colors.green),
        ),
        body: Column(children: <Widget>[
          const SizedBox(height: 20),
          Padding(
              padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 75),
              child: Row(children: const [
                Text(
                  "Detail ",
                  style: TextStyle(
                      fontSize: 30,
                      color: Colors.lightGreen,
                      fontWeight: FontWeight.w700),
                ),
                Text(
                  "Tagihan",
                  style: TextStyle(
                      fontSize: 30,
                      color: Colors.blue,
                      fontWeight: FontWeight.w700),
                ),
              ])),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 75),
            child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: <Widget>[
                  const SizedBox(height: 6.0),
                  Text(
                    "Kode Tagihan: " + tagihan.kode.toString(),
                    style: TextStyle(
                      fontSize: 20.0,
                      color: Colors.grey[800],
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 6.0),
                  Text(
                    "Tanggal Terbuat: " +
                        tagihan.tanggalTerbuat.substring(0, 10),
                    style: TextStyle(
                      fontSize: 14.0,
                      color: Colors.grey[500],
                    ),
                  ),
                  const SizedBox(height: 15.0),
                  Text(
                    "Status: " + (tagihan.isPaid ? "Lunas" : "Belum dibayar"),
                    style: TextStyle(
                      fontSize: 20.0,
                      color:
                          tagihan.isPaid ? Colors.green[800] : Colors.red[800],
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 6.0),
                  if (tagihan.tanggalBayar != null)
                    Text(
                      "Tanggal Dibayar: " +
                          tagihan.tanggalBayar!.substring(0, 10),
                      style: TextStyle(
                        fontSize: 14.0,
                        color: Colors.grey[500],
                      ),
                    ),
                  const SizedBox(height: 6.0),
                  Text(
                    "Jumlah Tagihan: Rp" +
                        formatCurrency.format(tagihan.jumlahTagihan).toString(),
                    style: TextStyle(
                      fontSize: 20.0,
                      color: Colors.grey[800],
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 6.0),
                  Text(
                    "Kode Appointment: " + tagihan.appointment.kode.toString(),
                    style: TextStyle(
                      fontSize: 20.0,
                      color: Colors.grey[800],
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 20.0),
                  if (!tagihan.isPaid)
                    OutlinedButton(
                      style: TextButton.styleFrom(
                        foregroundColor: Colors.blue,
                        disabledForegroundColor: Colors.red,
                      ),
                      onPressed: () => showDialog<String>(
                        context: context,
                        builder: (BuildContext _context) => AlertDialog(
                          title: const Text('Konfirmasi Pembayaran'),
                          content: Text(
                              'Apakah Anda yakin akan membayar tagihan ' +
                                  tagihan.kode.toString() +
                                  '?\n\n' +
                                  'Jumlah tagihan: Rp' +
                                  formatCurrency
                                      .format(tagihan.jumlahTagihan)
                                      .toString()),
                          actions: <Widget>[
                            TextButton(
                              onPressed: () => Navigator.pop(_context),
                              child: const Text('Cancel'),
                            ),
                            TextButton(
                              onPressed: () async {
                                final response = await http.get(
                                  Uri.parse(
                                      "https://apap-090.cs.ui.ac.id/api/v1/tagihan/" +
                                          LoginPage.username +
                                          "/bayar/" +
                                          tagihan.kode),
                                  headers: <String, String>{
                                    "Content-Type":
                                        "application/json;charset=UTF-8",
                                    "Authorization":
                                        "Bearer " + LoginPage.token,
                                  },
                                );
                                if (response.statusCode == 200 &&
                                    json.decode(
                                        response.body)["statusPembayaran"]) {
                                  Navigator.pop(_context);
                                  showDialog<String>(
                                    context: context,
                                    builder: (BuildContext _context) =>
                                        AlertDialog(
                                      title: const Text('Pembayaran Berhasil!'),
                                      content: Text('Tagihan dengan kode ' +
                                          tagihan.kode +
                                          ' telah lunas.'),
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
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) => const HomePage(
                                              title: 'RumahSehat')));
                                } else if (response.statusCode == 200 &&
                                    !json.decode(
                                        response.body)["statusPembayaran"]) {
                                  Navigator.pop(_context);
                                  showDialog<String>(
                                    context: context,
                                    builder: (BuildContext _context) =>
                                        AlertDialog(
                                      title: const Text('Pembayaran Gagal'),
                                      content: const Text(
                                          'Saldo Anda tidak cukup untuk melanjutkan proses pembayaran.'),
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
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) => const HomePage(
                                              title: 'RumahSehat')));
                                } else {
                                  Navigator.pop(_context);
                                  ScaffoldMessenger.of(context).showSnackBar(
                                      const SnackBar(
                                          content: Text(
                                              "Terjadi masalah dalam proses pembayaran tagihan.")));
                                }
                              },
                              child: const Text('Bayar'),
                            ),
                          ],
                        ),
                      ),
                      child: const Text('Bayar Tagihan'),
                    ),
                ]),
          ),
        ]));
  }
}
