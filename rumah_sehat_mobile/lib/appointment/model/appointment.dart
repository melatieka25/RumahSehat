import 'dart:convert';

import 'package:intl/intl.dart';

import '../../resep/model/resep.dart';


Appointment AppointmentFromJson(String str) => Appointment.fromJson(json.decode(str));

String AppointmentToJson(Appointment data) => json.encode(data.toJson());

AllAppointment AllAppointmentFromJson(String str) => AllAppointment.fromJson(json.decode(str));

String AllAppointmentToJson(AllAppointment data) => json.encode(data.toJson());

class AllAppointment {
  AllAppointment({
    required this.listAppointment,
  });

  List<Appointment> listAppointment;

  factory AllAppointment.fromJson(Map<String, dynamic> json) => AllAppointment(
    listAppointment: List<Appointment>.from(json["listAppointment"].map((x) => Appointment.fromJson(x))),
  );

  Map<String, dynamic> toJson() => {
    "listPasien": listAppointment[0].toJson(),
  };
}

class Appointment {
  Appointment(
      { required this.kode,
        required this.waktuAwal,
        required this.isDone,
        required this.resep,
        required this.tagihan,
        required this.pasien,
        required this.dokter,
        required this.namaDokter,
        required this.namaPasien,
      });

  String? kode;
  DateTime waktuAwal;
  bool isDone;
  Resep? resep;
  String? tagihan;
  String? pasien;
  String? dokter;
  String? namaDokter;
  String? namaPasien;

  factory Appointment.fromJson(Map<String, dynamic> json) => Appointment(
      kode: json["kode"],
      waktuAwal: DateTime.parse(json["waktuAwal"]),
      isDone: json["isDone"],
      resep : json["resep"] == null ? null : Resep.fromJson(json["resep"]),
      tagihan : json["tagihan"],
      pasien : json["pasien"],
      dokter: json["dokter"],
      namaDokter: json["namaDokter"],
      namaPasien: json["namaPasien"],
  );

  Map<String, dynamic> toJson() => {
    "kode": kode,
    "waktuAwal": DateFormat('yyyy-MM-dd HH:mm').format(waktuAwal),
    "isDone": isDone,
    "resep": resep,
    "tagihan": tagihan,
    "pasien": pasien,
    "dokter": dokter,
    "namaDokter": namaDokter,
    "namaPasien": namaPasien,
  };
}


