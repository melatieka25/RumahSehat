import 'dart:convert';

Pasien PasienFromJson(String str) => Pasien.fromJson(json.decode(str));

String PasienToJson(Pasien data) => json.encode(data.toJson());

class AllPasien {
  AllPasien({
    required this.listPasien,
  });

  List<Pasien> listPasien;

  factory AllPasien.fromJson(Map<String, dynamic> json) => AllPasien(
    listPasien: List<Pasien>.from(json["listPasien"].map((x) => AllPasien.fromJson(x))),
  );

  Map<String, dynamic> toJson() => {
    "listPasien": listPasien[0].toJson(),
  };
}

class Pasien {
  Pasien(
      { required this.nama,
        required this.role,
        required this.username,
        required this.password,
        required this.email,
        required this.saldo,
        required this.umur,
        required this.isSso,
      });

  String nama;
  String role;
  String username;
  String password;
  String email;
  int saldo;
  int umur;
  bool isSso;

  factory Pasien.fromJson(Map<String, dynamic> json) => Pasien(
    nama: json["nama"],
    role: json["role"],
    username: json["username"],
    password: json["password"],
    email: json["email"],
    saldo: json["saldo"],
    umur: json["umur"],
    isSso: json["isSso"],
  );

  Map<String, dynamic> toJson() => {
    "nama": nama,
    "role": role,
    "username": username,
    "password": password,
    "email": email,
    "saldo": saldo,
    "umur": umur,
    "isSso": isSso,
  };
}
