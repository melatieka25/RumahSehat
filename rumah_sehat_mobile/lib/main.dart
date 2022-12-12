import 'package:flutter/material.dart';
import 'package:rumah_sehat_mobile/registrasi_pasien/screen/form_registrasi_pasien.dart';
import 'package:rumah_sehat_mobile/widget/drawer.dart';
// import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

import 'login/login_page.dart';

void main() {
  runApp(const MyApp());
}


class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'RumahSehat',
        debugShowCheckedModeBanner: false,
        theme: ThemeData(
          // This is the theme of your application.
          //
          // Try running your application with "flutter run". You'll see the
          // application has a blue toolbar. Then, without quitting the app, try
          // changing the primarySwatch below to Colors.green and then invoke
          // "hot reload" (press "r" in the console where you ran "flutter run",
          // or simply save your changes to "hot reload" in a Flutter IDE).
          // Notice that the counter didn't reset back to zero; the application
          // is not restarted.
          primarySwatch: Colors.green,
        ),
        home: const HomePage(title: 'RumahSehat'),
        routes: <String, WidgetBuilder>{
          "home": (BuildContext context) => const HomePage(title: 'RumahSehat'),
          "login": (BuildContext context) => const LoginPage(),
        });
  }
}

class HomePage extends StatefulWidget {
  const HomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    if (LoginPage.username == "") {
      return LoginPage();
    } else {
      return Scaffold(
        appBar: AppBar(
          title:  Image.asset('assets/images/long-logo.png', width: 200),
          backgroundColor: Colors.white,
          iconTheme: IconThemeData(color: Colors.green),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text(
                'Halo ' +
                    LoginPage.username + '!',
                style: const TextStyle(
                    fontSize: 20,
                    color: Colors.black,
                    fontWeight: FontWeight.w700),
              ),
              const SizedBox(height: 10),
              Text(
                'Selamat datang di RumahSehat!',
                style: const TextStyle(
                    fontSize: 20,
                    color: Colors.black,
                    fontWeight: FontWeight.w700),
              ),
              Hero(
                tag: 'RumahSehat',
                child: Image.asset('assets/images/long-logo.png', width: 320),
              ),
              ClipRRect(
                borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(8.0),
                  topRight: Radius.circular(8.0),
                ),
                child: Image.network("https://arsitagx-master.s3.ap-southeast-1.amazonaws.com/img-medium/13685/9721/muhammad-imaaduddin-rumah-sakit-imc-bintaro1539051152-m.jpeg",
                    width: 300, height: 200, fit: BoxFit.cover),
              ),
              const SizedBox(height: 30),
              const Text('Ketuk drawer untuk menjelajahi aplikasi'),
            ],
          ),
        ),
        drawer: const MyDrawer(),
      );
    }
  }
}
