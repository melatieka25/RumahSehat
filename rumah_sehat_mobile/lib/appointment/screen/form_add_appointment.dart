import 'dart:convert';
import 'dart:math';
import 'package:flutter/material.dart';
import 'package:flutter_cupertino_datetime_picker/flutter_cupertino_datetime_picker.dart';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';
import 'dart:ui';
import 'package:rumah_sehat_mobile/appointment/model/appointment.dart';
import 'package:rumah_sehat_mobile/appointment/screen/list_appointment.dart';
import 'package:rumah_sehat_mobile/login/login_page.dart';

import '../../main.dart';
import '../../resep/model/resep.dart';

class Dialog extends StatelessWidget {

  String title;
  String content;
  VoidCallback continueCallBack;
  int statusCode;

  Dialog(this.title, this.content, this.continueCallBack, this.statusCode);
  TextStyle textStyle = TextStyle (color: Colors.black);

  @override
  Widget build(BuildContext context) {
    if (statusCode == 200) {
      return BackdropFilter(
          filter: ImageFilter.blur(sigmaX: 6, sigmaY: 6),
          child: AlertDialog(
            title: new Text(title, style: textStyle,),
            content: new Text(content, style: textStyle,),
            actions: <Widget>[
              TextButton(
                child: Text("Kembali"),
                onPressed: () {
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) => const ListAppointment()));
                },
              ),
            ],
          ));
    } else {
      return BackdropFilter(
          filter: ImageFilter.blur(sigmaX: 6, sigmaY: 6),
          child: AlertDialog(
            title: new Text(title, style: textStyle,),
            content: new Text(content, style: textStyle,),
            actions: <Widget>[
              TextButton(
                child: Text("Kembali"),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          ));
    }
  }
}

class AppointmentForm extends StatefulWidget {
  const AppointmentForm({Key? key}) : super(key: key);

  @override
  AppointmentFormState createState() {
    return AppointmentFormState();
  }
}

var _controllerDate = TextEditingController();

class AppointmentFormState extends State<AppointmentForm> {
  String finalResponse = "";
  final _formKey = GlobalKey<FormState>();

  _showDialog(BuildContext context, int statusCode) {

    VoidCallback continueCallBack = () => {
      Navigator.of(context).pop(),
      // code on continue comes here
    };
    Dialog alert = Dialog("", "", continueCallBack, statusCode);
    if (statusCode == 200) {
      alert = Dialog("Success!", "Appointment berhasil dibuat!" ,continueCallBack, statusCode);
    } else {
      alert = Dialog("Gagal!", "Dokter sudah memiliki appointment di jam tersebut!" ,continueCallBack, statusCode);
    }


    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }

  Future<void> createAppointment(String? kode, DateTime waktuAwal, bool isDone, Resep? resep, String? tagihan, String pasien, String dokter, String? namaDokter, String? namaPasien) async {

    Appointment newAppointment = Appointment(kode: kode, waktuAwal: waktuAwal, isDone: isDone, resep: resep, tagihan: tagihan, pasien: pasien, dokter: dokter, namaDokter: namaDokter, namaPasien: namaPasien);
    print(AppointmentToJson(newAppointment));

    final response = await http.post(
      Uri.parse('http://10.0.2.2:8081/api/v1/appointment/create'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: AppointmentToJson(newAppointment)
      ,
    );

    if (response.statusCode == 200) {
      _showDialog(context, response.statusCode);

      _controllerDate.clear();
      date = "";

      FocusScope.of(context).unfocus();

      //return Appointment.fromJson(jsonDecode(response.body));
    } else {
      _showDialog(context, response.statusCode);
    }
  }

  Future<void> _savingData() async {
    final validation = _formKey.currentState!.validate();

    if (!validation) {
      return;
    }

    _formKey.currentState!.save();
    createAppointment(null, dateTimeChosen, false, null, null, LoginPage.username, _valDokter, null, null);
  }
  final ScrollController _firstController = ScrollController();

  @override
  void initState() {
    _controllerDate.text = "";
    getDokter();
    super.initState();
  }

  var _valDokter;
  var date;
  late DateTime dateTimeChosen;
  List _dataDokter = [];

  void getDokter() async {
    var url = Uri.parse('http://10.0.2.2:8081/api/v1/dokter');
    final response = await http.get(url, headers: {"Access-Control_Allow_Origin": "*"});
    if (response.statusCode == 200) {
      var data = jsonDecode(response.body);
      setState(() {
        _dataDokter = data;
      });
    }
    print(_dataDokter);
  }

  dateTimePickerWidget(BuildContext context) async {
    return DatePicker.showDatePicker(
      context,
      dateFormat: 'dd MM yyyy HH:mm',
      initialDateTime: DateTime.now(),
      minDateTime: DateTime.now(),
      maxDateTime: DateTime(2100),
      onMonthChangeStartWithFirstDate: true,
      onConfirm: (dateTime, List<int> index) {
        DateTime selectedDate = dateTime;
        setState(() {
          date = DateFormat('yyyy-MM-dd HH:mm').format(selectedDate);
          dateTimeChosen = selectedDate;
          _controllerDate.text = date;
        });
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    // Build a Form widget using the _formKey created above.
    return Scaffold(
      appBar: AppBar(
        title: Text('Membuat Appointment'),
        backgroundColor: Colors.blueGrey,
      ),
      body: Container(
        padding: EdgeInsets.all(20.0),
        child: Form(
          key: _formKey,
          child: Scrollbar(
            thumbVisibility: true,
            controller: _firstController,
            child:
            ListView(
              controller: _firstController,
              children: [
                SizedBox(height: 30),
                const Text('Tanggal dan Waktu Appointment'),
                TextFormField(
                  decoration: InputDecoration(
                      icon: Icon(Icons.calendar_today),
                      labelText: "Masukkan tanggal dan waktu"
                  ),
                  onSaved: (value){
                  },
                  controller: _controllerDate,
                  onTap: () async {
                    await dateTimePickerWidget(context);
                  },
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan tanggal dan waktu!';
                    }
                    return null;
                  },
                ),
                SizedBox(height: 30),
                const Text('Dokter'),
                DropdownButtonFormField(
                    hint: Text("Pilih dokter"),
                    value: _valDokter,
                    onChanged: (value) {
                      setState(() {
                        _valDokter = value;
                      });
                    },
                    items: _dataDokter.map((item) {
                    return DropdownMenuItem(
                        child: Text(item['nama'] + ' - ' + item['tarif'].toString()),
                        value: item['uuid'],
                    );
                  }).toList(),
                  validator: (value) {
                    if (value == null) {
                      return 'Pilih dokter!';
                    }
                    return null;
                  },
                ),
                SizedBox(height: 40),
                ElevatedButton(
                  onPressed: () async {
                    _savingData();
                  },
                  child: const Text('Create'),
                ),
              ],
            )
          ),
        ),
      ),
    );
  }

}