import 'dart:convert';
import 'dart:math';
import 'package:flutter/material.dart';
import 'package:flutter_cupertino_datetime_picker/flutter_cupertino_datetime_picker.dart';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';
import 'dart:ui';
import 'package:rumah_sehat_mobile/appointment/model/appointment.dart';
import 'package:rumah_sehat_mobile/login/login_page.dart';
import 'package:rumah_sehat_mobile/registrasi_pasien/model/pasien.dart';

import '../../main.dart';

class ListAppointment extends StatefulWidget {
  const ListAppointment({Key? key}) : super(key: key);

  @override
  ListAppointmentState createState() {
    return ListAppointmentState();
  }
}

class ListAppointmentState extends State<ListAppointment> {

  @override
  void initState() {
    getListAppointment();
    super.initState();
  }
  List _listAppointment = [];
  void getListAppointment() async {
    var url = Uri.parse('http://10.0.2.2:8081/api/v1/appointment/' + LoginPage.username);
    final response = await http.get(url, headers: {"Access-Control_Allow_Origin": "*"});
    print(response.body);
    if (response.statusCode == 200) {
      var data = jsonDecode(response.body);
      _listAppointment = data;
    }
  }

  @override
  Widget build(BuildContext context) {
    // Build a Form widget using the _formKey created above.
    return Scaffold(
      appBar: AppBar(
        title: Text('List Appointment'),
        backgroundColor: Colors.blueGrey,
      ),
      body: Container(
        padding: EdgeInsets.all(20.0),
          child: Table(
            border: TableBorder.all(width: 1.0, color: Colors.black),
            children: [
              for (var appointment in _listAppointment) TableRow(children: [
                TableCell(child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: <Widget>[
                    
                  ],
                ))
              ])
            ],
          ),
      ),
    );
  }

}