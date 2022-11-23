import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:ui';

// https://docs.flutter.dev/cookbook/forms/validation
class Dialog extends StatelessWidget {

  String title;
  String content;
  VoidCallback continueCallBack;

  Dialog(this.title, this.content, this.continueCallBack);
  TextStyle textStyle = TextStyle (color: Colors.black);

  @override
  Widget build(BuildContext context) {
    return BackdropFilter(
        filter: ImageFilter.blur(sigmaX: 6, sigmaY: 6),
        child:  AlertDialog(
          title: new Text(title,style: textStyle,),
          content: new Text(content, style: textStyle,),
          actions: <Widget>[
            TextButton(
              child: Text("Kembali"),
              onPressed: () {
                continueCallBack();
                Navigator.of(context).pop();
              },
            ),
          ],
        ));
  }
}

// Define a custom Form widget.
class MyCustomForm extends StatefulWidget {
  const MyCustomForm({Key? key}) : super(key: key);

  @override
  MyCustomFormState createState() {
    return MyCustomFormState();
  }
}

var _controllerNama = TextEditingController();
var _controllerUsername = TextEditingController();
var _controllerPassword = TextEditingController();
var _controllerEmail = TextEditingController();
var _controllerSaldo = TextEditingController();
var _controllerUmur = TextEditingController();

// Define a corresponding State class.
// This class holds data related to the form.
class MyCustomFormState extends State<MyCustomForm> {
  String finalResponse = "";
  final _formKey = GlobalKey<FormState>();

  _showDialog(BuildContext context) {

    VoidCallback continueCallBack = () => {
      Navigator.of(context).pop(),
      // code on continue comes here
    };
    Dialog alert = Dialog("Hore akun tersimpan!", "Selamat datang " + _controllerNama.text + "!",continueCallBack);


    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }

  Future<Pasien> createPasien(String title, String? image, String type, String city, int price,
      String location, String? description, String? link, String? resultTime, String? schedule,
      String? phone, String? email) async {

    List<CovidTest> tesCovids = [];
    CovidTest newTest = CovidTest(type: type, title: title, city: city, price: price, location: location, testImage: image, description: description, link: link, resultTime: resultTime, schedule: schedule, phone: phone, email: email);
    tesCovids.add(newTest);
    TesCovid data = TesCovid(covidTests: tesCovids);


    final response = await http.post(
      Uri.parse('https://lindungipeduli.herokuapp.com/covid-test/kirim-data'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: tesCovidToJson(data)
      ,
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      _showDialog(context);

      _controllerTitle.clear();
      _controllerImage.clear();
      _controllerLocation.clear();
      _controllerPrice.clear();
      _controllerDescription.clear();
      _controllerLink.clear();
      _controllerResultTime.clear();
      _controllerSchedule.clear();
      _controllerPhone.clear();
      _controllerEmail.clear();

      FocusScope.of(context).unfocus();

      return TesCovid.fromJson(jsonDecode(response.body));
    } else {
      print(response.body);
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      throw Exception('Pembuatan tes gagal.');
    }
  }
  Future<void> _savingData() async{

    final validation = _formKey.currentState!.validate();

    if (!validation){
      return;
    }

    _formKey.currentState!.save();

    createTest(_controllerTitle.text, _controllerImage.text,
        type, city, int.parse(_controllerPrice.text), _controllerLocation.text, _controllerDescription.text,
        _controllerLink.text, _controllerResultTime.text, _controllerSchedule.text,
        _controllerPhone.text, _controllerEmail.text);

  }

  final ScrollController _firstController = ScrollController();
  String type = "";
  String city = "";

  @override
  Widget build(BuildContext context) {
    // Build a Form widget using the _formKey created above.
    return Scaffold(
      appBar: AppBar(
        title: Text('Tambah Tes Covid'),
        backgroundColor: Colors.blueGrey,
      ),
      body: Container(
        padding: EdgeInsets.all(20.0),
        child: Form(
          key: _formKey,
          child: Scrollbar(
            isAlwaysShown: true,
            controller: _firstController,
            child:
            ListView(
              controller: _firstController,
              children: [
                SizedBox(height: 30),
                const Text('Judul'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  onSaved: (value) {
                  },
                  controller: _controllerTitle,
                  // The validator receives the text that the user has entered.
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan judul tes covid';
                    }
                    return null;
                  },
                ),
                SizedBox(height: 30),
                SelectFormField(
                    type: SelectFormFieldType.dropdown, // or can be dialog
                    initialValue: "Pilih Tipe",
                    labelText: 'Tipe',
                    items: tesType,
                    onChanged: (val) => setState(() {
                      type = val;
                      print(type);
                    })
                ),
                SizedBox(height: 30),
                SelectFormField(
                    type: SelectFormFieldType.dropdown, // or can be dialog
                    initialValue: 'Pilih Kota',
                    labelText: 'Kota/Kabupaten',
                    items: tesCity,
                    onChanged: (val) => setState(() {
                      city = val;
                      print(val);
                    })
                ),
                SizedBox(height: 30),
                const Text('Lokasi'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  controller: _controllerLocation,
                  // The validator receives the text that the user has entered.
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan lokasi';
                    }
                    return null;
                  },
                ),
                SizedBox(height: 30),
                const Text('Harga'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                    hintText: '120000',

                  ),
                  controller: _controllerPrice,
                  // The validator receives the text that the user has entered.
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan harga';
                    }
                    return null;
                  },
                  onSaved: (value) {
                    // price = int.parse(value!);
                  },
                ),
                SizedBox(height: 30),
                const Text('Gambar'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                    hintText: 'http://www.google.com',
                  ),
                  controller: _controllerImage,
                  // The validator receives the text that the user has entered.
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan link gambar';
                    }
                    return null;
                  },
                  onSaved: (value) {
                    //testImage = value!;
                  },
                ),
                SizedBox(height: 30),
                const Text('Deskripsi'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  // onSaved: (value) {
                  //   title = value!;
                  // },
                  controller: _controllerDescription,
                  onSaved: (value) {
                    //description = value!;
                  },
                  // The validator receives the text that the user has entered.
                ),
                SizedBox(height: 30),
                const Text('Tautan'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                    hintText: 'http://www.google.com',
                  ),
                  controller: _controllerLink,
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan Link Tujuan';
                    }
                    return null;
                  },
                  // The validator receives the text that the user has entered.
                ),
                SizedBox(height: 30),
                const Text('Durasi'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  controller: _controllerResultTime,
                  onSaved: (value) {
                    //resultTime = value!;
                  },
                  // The validator receives the text that the user has entered.
                ),
                SizedBox(height: 30),
                const Text('Jadwal'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                    hintText: '00:00:00',
                  ),

                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Masukkan jadwal dengan format mm:dd';
                    }
                    return null;
                  },
                  controller: _controllerSchedule,
                  onSaved: (value) {
                    //schedule = value!;
                  },
                  // The validator receives the text that the user has entered.
                ),
                SizedBox(height: 30),
                const Text('Nomor Telepon'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  controller: _controllerPhone,
                  onSaved: (value) {
                    //phone = value!;
                  },
                  // The validator receives the text that the user has entered.
                ),
                SizedBox(height: 30),
                const Text('Alamat Email'),
                TextFormField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                  ),
                  // onSaved: (value) {
                  //   title = value!;
                  // },
                  controller: _controllerEmail,
                  onSaved: (value) {
                    //email = value!;
                  },
                  // The validator receives the text that the user has entered.
                ),

                SizedBox(height: 40),
                ElevatedButton(
                  onPressed: () async {
                    // createTest("tes covid", "https://fajar.co.id/wp-content/uploads/img/no-image.jpg", "PCR", "Bekasi", 200000,
                    //     "jakarta", "tidak ada", "https://fajar.co.id/wp-content/uploads/img/no-image.jpg", "5 jam", "08:00", "0888888", "mitra@gmail.com");
                    // Validate returns true if the form is valid, or false otherwise.
                    _savingData();
                  },
                  child: const Text('Submit'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}