// To parse this JSON data, do
//
//     final tesCovid = tesCovidFromJson(jsonString);

import 'dart:convert';

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
      });

  DateTime waktuAwal;
  bool isDone;


  factory Appointment.fromJson(Map<String, dynamic> json) => Appointment(
      waktuAwal: json["waktuAwal"],
      isDone: json["isDone"],
  );

  Map<String, dynamic> toJson() => {
    "waktuAwal": waktuAwal,
    "isDone": isDone,
  };
}
