import 'package:flutter/material.dart';
import 'package:rumah_sehat_mobile/login/login_page.dart';
import 'package:rumah_sehat_mobile/tagihan/screen/tagihan_list.dart';
import 'package:rumah_sehat_mobile/saldo/topup_saldo.dart';
import '../appointment/screen/list_appointment.dart';
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
            leading: Icon(Icons.medication),
            title: Text('Jadwal Appointment'),
            // selected: _selectedDestination == 1,
            // onTap: () => selectDestination(1),
            onTap: () => {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const ListAppointment())),
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
                  MaterialPageRoute(builder: (context) => const TopupSaldoPage())),
            },
          ),
          ListTile(
            leading: Icon(Icons.payments),
            title: Text('Daftar Tagihan'),
            // selected: _selectedDestination == 1,
            // onTap: () => selectDestination(1),
            onTap: () => {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const TagihanListScreen())),
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
                  LoginPage.token =  "";
                });
                Navigator.of(context).pushAndRemoveUntil(
                    MaterialPageRoute(
                        builder: (context) =>
                        const LoginPage()),
                        (route) => false);

                ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Anda berhasil Logout')));
              }
            },
          ),
        ],
      ),
    );
  }
}
