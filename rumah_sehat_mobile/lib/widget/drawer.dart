import 'package:flutter/material.dart';
import 'package:rumah_sehat_mobile/login/login_page.dart';
import 'package:rumah_sehat_mobile/registrasi_pasien/screen/form_registrasi_pasien.dart';

import '../main.dart';


class MyDrawer extends StatefulWidget {
  const MyDrawer({Key? key}) : super(key: key);

  @override
  State<MyDrawer> createState() => _MyDrawerState();
}

class _MyDrawerState extends State<MyDrawer> {

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        // Important: Remove any padding from the ListView.
        padding: EdgeInsets.zero,
        children: <Widget>[
          // Nama Web
          const Padding(
            padding: EdgeInsets.all(16.0),
            child: Text('RumahSehat',
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                )),
          ),
          const Divider(
            height: 1,
            thickness: 1,
          ),

          // Pages
          ListTile(
            leading: Icon(Icons.home),
            title: Text('Home'),
            onTap: () {
              Navigator.push(
                  context, MaterialPageRoute(builder: (context) => const HomePage(title: 'RumahSehat')));
              // Navigator.of(context).pushNamed(
              //   '/',
              // );
            },
          ),
          ListTile(
            leading: Icon(Icons.app_registration),
            title: Text('Registrasi'),
            // selected: _selectedDestination == 1,
            // onTap: () => selectDestination(1),
            onTap: () => {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => PasienForm())),
            },
          ),
          ListTile(
            leading: Icon(Icons.medication),
            title: Text('Jadwal Appointment'),
            // selected: _selectedDestination == 1,
            // onTap: () => selectDestination(1),
            onTap: () => {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const HomePage(title: 'RumahSehat'))),
            },
          ),
          ListTile(
            leading: Icon(Icons.person),
            title: Text('Profile'),
            // selected: _selectedDestination == 1,
            // onTap: () => selectDestination(1),
            onTap: () => {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const HomePage(title: 'RumahSehat'))),
            },
          ),
          ListTile(
            leading: Icon(Icons.account_balance_wallet),
            title: Text('Topup Saldo'),
            // selected: _selectedDestination == 1,
            // onTap: () => selectDestination(1),
            onTap: () => {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const HomePage(title: 'RumahSehat'))),
            },
          ),
          ListTile(
            leading: Icon(Icons.payments),
            title: Text('Daftar Tagihan'),
            // selected: _selectedDestination == 1,
            // onTap: () => selectDestination(1),
            onTap: () => {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const HomePage(title: 'RumahSehat'))),
            },
          ),
          ListTile(
            leading: Icon(Icons.logout),
            title: Text('Logout'),
            onTap: () async {
              if (LoginPage.roles != '') {
                setState(() {
                  LoginPage.roles = "";
                  LoginPage.username =  "";
                });
                Navigator.of(context).pushAndRemoveUntil(
                    new MaterialPageRoute(
                        builder: (context) =>
                        new LoginPage()),
                        (route) => false);

                ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Anda berhasil Logout')));
              };
            },
          ),

          // ListTile(
          //   leading: Icon(Icons.book),
          //   title: Text('Add-Thread'),
          //   onTap: () {
          //     if (LoginPage.roles != "") {
          //       // Kalau belum login, role defaultnya "", kyknya(iya bener)
          //       // Navigator.of(context).pushNamed(
          //       //   '/Add-Thread',
          //       // );
          //       Navigator.push(context,
          //           MaterialPageRoute(builder: (context) => AddThread()));
          //     } else {
          //       // Navigator.of(context).pushNamed(
          //       //   '/register',
          //       // );
          //       Navigator.push(context,
          //           MaterialPageRoute(builder: (context) => RegisterPage()));
          //     }
          //     ;
          //   },
          // ),
          // ListTile(
          //   leading: Icon(Micon.syringe),
          //   title: Text('Daftar Vaksin'),
          //   onTap: () => {
          //     Navigator.push(context,
          //         MaterialPageRoute(builder: (context) => DaftarVaksin())),
          //   },
          // ),
          // ListTile(
          //   leading: Icon(Micon.book_medical),
          //   title: Text('Kamus Obat'),
          //
          //   // selected: _selectedDestination == 2,
          //   // onTap: () => selectDestination(2),
          //   onTap: () => {
          //     Navigator.of(context).push(MaterialPageRoute(
          //         builder: (context) => KamusHomepage()))
          //   },
          //
          // ),
          // ListTile(
          //   leading: Icon(Icons.local_pharmacy),
          //   title: Text('Apotek Daring'),
          //   onTap: () => {
          //     Navigator.push(context, MaterialPageRoute(builder: (context) {
          //       return KatalogApotek();
          //     }))
          //   },
          // ),
          // ListTile(
          //   leading: Icon(Icons.article),
          //   title: Text('Artikel'),
          //   // selected: _selectedDestination == 2,
          //   // onTap: () => selectDestination(2),
          //   onTap: () => {
          //     Navigator.push(context, MaterialPageRoute(builder: (context) {
          //       return ArtikelScreen();
          //     }))
          //   },
          // ),
          // const Divider(
          //   height: 1,
          //   thickness: 1,
          // ),
          //
          // // Account
          // const Padding(
          //   padding: EdgeInsets.all(16.0),
          //   child: Text(
          //     'Akun',
          //   ),
          // ),
          // Visibility(
          //   visible: isVisible,
          //   child: ListTile(
          //     leading: Icon(Icons.login),
          //     title: Text('Masuk'),
          //     onTap: () {
          //       if (LoginPage.roles != "") {
          //         ScaffoldMessenger.of(context).showSnackBar(
          //             const SnackBar(content: Text('Anda Telah Login')));
          //       } else {
          //         Navigator.push(context,
          //             MaterialPageRoute(builder: (context) => LoginPage()));
          //       }
          //     },
          //   ),
          // ),
          // Visibility(
          //   visible: isVisible,
          //   child: ListTile(
          //     leading: Icon(Icons.app_registration),
          //     title: Text('Daftar'),
          //     onTap: () {
          //       if (LoginPage.roles != "") {
          //         ScaffoldMessenger.of(context).showSnackBar(
          //             const SnackBar(content: Text('Anda Telah Login')));
          //       } else {
          //         Navigator.push(context,
          //             MaterialPageRoute(builder: (context) => RegisterPage()));
          //       }
          //     },
          //   ),
          // ),
        ],
      ),
    );
  }
}
