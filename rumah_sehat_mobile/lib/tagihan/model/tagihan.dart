import 'dart:convert';

import 'package:rumah_sehat_mobile/appointment/model/appointment.dart';

// ignore: non_constant_identifier_names
AllTagihan AllTagihanFromJson(String str) =>
    AllTagihan.fromJson(json.decode(str));

String AllTagihanToJson(AllTagihan data) => json.encode(data.toJson());

class AllTagihan {
  AllTagihan({
    required this.listTagihan,
  });

  List<Tagihan> listTagihan;

  factory AllTagihan.fromJson(Map<String, dynamic> json) => AllTagihan(
        listTagihan: List<Tagihan>.from(
            json["listTagihan"].map((x) => Tagihan.fromJson(x))),
      );

  Map<String, dynamic> toJson() => {
        "listTagihan": listTagihan[0].toJson(),
      };
}

class Tagihan {
  Tagihan({
    required this.kode,
    required this.tanggalTerbuat,
    required this.isPaid,
    required this.tanggalBayar,
    required this.jumlahTagihan,
    required this.appointment,
  });

  String kode;
  String tanggalTerbuat;
  bool isPaid;
  String? tanggalBayar;
  int jumlahTagihan;
  Appointment appointment;

  factory Tagihan.fromJson(Map<String, dynamic> json) => Tagihan(
        kode: json["kode"],
        tanggalTerbuat: json["tanggalTerbuat"],
        isPaid: json["isPaid"],
        tanggalBayar: json["tanggalBayar"],
        jumlahTagihan: json["jumlahTagihan"],
        appointment: Appointment.fromJson(json["appointment"]),
      );

  Map<String, dynamic> toJson() => {
        "kode": kode,
        "tanggalTerbuat": tanggalTerbuat,
        "isDone": isPaid,
        "tanggalBayar": tanggalBayar,
        "jumlahTagihan": jumlahTagihan,
        "appointment": appointment,
      };
}
