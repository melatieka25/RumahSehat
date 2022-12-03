// To parse this JSON data, do
//
//     final tesCovid = tesCovidFromJson(jsonString);

import 'dart:convert';
import 'package:rumah_sehat_mobile/registrasi_pasien/model/pasien.dart';

Appointment AppointmentFromJson(String str) => Appointment.fromJson(json.decode(str));

String AppointmentToJson(Appointment data) => json.encode(data.toJson());

// class AllPasien {
//   AllPasien({
//     required this.listPasien,
//   });
//
//   List<Pasien> listPasien;
//
//   factory AllPasien.fromJson(Map<String, dynamic> json) => AllPasien(
//     listPasien: List<Pasien>.from(json["listPasien"].map((x) => AllPasien.fromJson(x))),
//   );
//
//   Map<String, dynamic> toJson() => {
//     "listPasien": listPasien[0].toJson(),
//   };
// }

class Appointment {
  Appointment(
      { required this.waktuAwal,
        required this.isDone,
        required this.resep,
        required this.tagihan,
        required this.pasien,
        required this.dokter,
      });

  String waktuAwal;
  bool isDone;
  int? resep;
  String? tagihan;
  String pasien;
  String dokter;


  factory Appointment.fromJson(Map<String, dynamic> json) => Appointment(
      waktuAwal: json["waktuAwal"],
      isDone: json["isDone"],
      resep : json["resep"],
      tagihan : json["tagihan"],
      pasien : json["pasien"],
      dokter: json["dokter"],
  );

  Map<String, dynamic> toJson() => {
    "waktuAwal": waktuAwal,
    "isDone": isDone,
    "resep": resep,
    "tagihan": tagihan,
    "pasien": pasien,
    "dokter": dokter,
  };
}


