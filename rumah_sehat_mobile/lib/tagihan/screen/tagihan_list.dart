import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:rumah_sehat_mobile/login/login_page.dart';
import 'dart:async';

import '../../widget/drawer.dart';
import '../model/tagihan.dart';
import '../widget/tagihan_template.dart';


class TagihanListScreen extends StatelessWidget {
  const TagihanListScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      // https://flutter.dev/docs/development/ui/assets-and-images
        theme: ThemeData(
          // Define the default brightness and colors.
          brightness: Brightness.light,
          primaryColor: Colors.lightBlue[800],

          // Define the default font family.
          fontFamily: 'Fredoka One',

          // Define the default `TextTheme`. Use this to specify the default
          // text styling for headlines, titles, bodies of text, and more.
          textTheme: const TextTheme(
            headline6: TextStyle(fontSize: 24.0),
          ),
        ),
        home: Scaffold(
          body: Container(
            child: MyCardWidget(),
          ),
        )
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

  Future<List<Tagihan>> _fetchData() async {
    var url = Uri.parse(
        'https://apap-090.cs.ui.ac.id/api/v1/tagihan/' + LoginPage.username);
    var response =
    await http.get(url, headers: {"Access-Control_Allow_Origin": "*", "Authorization": "Bearer " + LoginPage.token});

    List<Tagihan> listTagihan = AllTagihanFromJson(response.body).listTagihan;
    setState(() {
      _listTagihan = listTagihan;
    });
    return listTagihan;
  }

  final ScrollController _firstController = ScrollController();
  String query = "";
  List<Tagihan> _listTagihan = [];
  late Future<List<Tagihan>> _futureTagihan;

  @override
  void initState() {
    super.initState();
    _futureTagihan = _fetchData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title:  Image.asset('assets/images/long-logo.png', width: 200),
          backgroundColor: Colors.white,
          iconTheme: IconThemeData(color: Colors.green),
        ),
        backgroundColor: Colors.white,
        //https://api.flutter.dev/flutter/material/FloatingActionButton-class.html
        drawer: const MyDrawer(),
        body: Column(
            children: <Widget>[
              const SizedBox(height: 20),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10, horizontal: 110),
                child: Row(
                  children: [
                    const Text( "Daftar", style: TextStyle(
                        fontSize: 30,
                        color: Colors.lightGreen,
                        fontWeight: FontWeight.w700),
                    ),
                    const Text( "Tagihan", style: TextStyle(
                        fontSize: 30,
                        color: Colors.blue,
                        fontWeight: FontWeight.w700),
                    ),
                  ],
                ),
              ),
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.all(10),
                  child: FutureBuilder<List<Tagihan>>(
                    future: _futureTagihan,
                    builder: (context, snapshot) {
                      if (snapshot.hasData) {
                        if (_listTagihan.length == 0){
                          return const Text("Kamu tidak memiliki tagihan apapun.");
                        } else {
                          return Scrollbar(
                              thumbVisibility: true,
                              controller: _firstController,
                              child: ListView.builder(
                                  controller: _firstController,
                                  itemCount: _listTagihan.length,
                                  itemBuilder: (context, index) {
                                    return tagihanTemplate(
                                        _listTagihan[index], context);
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
