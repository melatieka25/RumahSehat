import 'dart:convert';
import 'dart:math';
import 'package:flutter/material.dart';
import 'package:flutter_cupertino_datetime_picker/flutter_cupertino_datetime_picker.dart';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';
import 'dart:ui';
import 'package:rumah_sehat_mobile/appointment/model/appointment.dart';
import 'package:rumah_sehat_mobile/appointment/screen/form_add_appointment.dart';
import 'package:rumah_sehat_mobile/appointment/widget/appointment_template.dart';
import 'package:rumah_sehat_mobile/login/login_page.dart';
import '../../widget/drawer.dart';

class ListAppointment extends StatelessWidget {
  const ListAppointment({Key? key}) : super(key: key);

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
  Future<List<Appointment>> _fetchData() async {
    var url = Uri.parse(
        'http://10.0.2.2:8081/api/v1/appointment/' + LoginPage.username);
    var response = await http.get(url, headers: {"Access-Control_Allow_Origin": "*", "Authorization": "Bearer " + LoginPage.token});
    List<Appointment> listAppointment = AllAppointmentFromJson(response.body).listAppointment;
    setState(() {
      _listAppointment = listAppointment;
    });
    return listAppointment;
  }

  final ScrollController _firstController = ScrollController();
  List<Appointment> _listAppointment = [];

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
                padding: EdgeInsets.symmetric(vertical: 10, horizontal: 70),
                child: Row(
                  children: [
                    const Text( "Daftar ", style: TextStyle(
                        fontSize: 30,
                        color: Colors.lightGreen,
                        fontWeight: FontWeight.w700),
                    ),
                    const Text( "Appointment", style: TextStyle(
                        fontSize: 30,
                        color: Colors.blue,
                        fontWeight: FontWeight.w700),
                    ),
                  ],
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
                    MaterialPageRoute(builder: (context) => AppointmentForm()),
                  );
                },
                child: Text('Buat Appointment'),
              ),
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.all(10),
                  child: FutureBuilder<List<Appointment>>(
                    future: _fetchData(),
                    builder: (context, snapshot) {
                      if (snapshot.hasData) {
                        if (_listAppointment.length == 0){
                          return const Text("Kamu tidak memiliki appointment apapun.");
                        } else {
                          return Scrollbar(
                              thumbVisibility: true,
                              controller: _firstController,
                              child: ListView.builder(
                                  controller: _firstController,
                                  itemCount: _listAppointment.length,
                                  itemBuilder: (context, index) {
                                    return appointmentTemplate(
                                        _listAppointment[index], context);
                                  })
                          );
                        }
                      } else if (snapshot.hasError) {
                        return Text('${snapshot.error}');
                      }

                      // By default, show a loading spinner.
                      return Center(child: Column(
                        children: const [
                          SizedBox(height: 100),
                          Text("Sedang mengambil data.."),
                          SizedBox (height: 20),
                          SizedBox( width: 30, height: 30, child: CircularProgressIndicator()),
                        ],
                      ),
                      );
                    },
                  ),
                ),)
            ]
        )
    );
  }
}

