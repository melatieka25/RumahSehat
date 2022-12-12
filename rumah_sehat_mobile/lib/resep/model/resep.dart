// To parse this JSON data, do
//
//     final tesCovid = tesCovidFromJson(jsonString);

// ignore_for_file: non_constant_identifier_names

import 'dart:convert';

import 'package:intl/intl.dart';

Resep ResepFromJson(String str) => Resep.fromJson(json.decode(str));

String ResepToJson(Resep data) => json.encode(data.toJson());

AllResep AllResepFromJson(String str) => AllResep.fromJson(json.decode(str));

String AllResepToJson(AllResep data) => json.encode(data.toJson());

class AllResep {
  AllResep({
    required this.listResep,
  });

  List<Resep> listResep;

  factory AllResep.fromJson(Map<String, dynamic> json) => AllResep(
        listResep:
            List<Resep>.from(json["listResep"].map((x) => Resep.fromJson(x))),
      );

  Map<String, dynamic> toJson() => {
        "listPasien": listResep[0].toJson(),
      };
}

class Resep {
  Resep({
    required this.id,
    required this.isDone,
    required this.createdAt,
    required this.listJumlah,
    required this.namaDokter,
    required this.namaPasien,
    required this.namaApoteker,
  });

  int id;
  bool isDone;
  DateTime createdAt;
  List listJumlah;
  String? namaDokter;
  String? namaPasien;
  String? namaApoteker;

  factory Resep.fromJson(Map<String, dynamic> json) => Resep(
        id: json["id"],
        isDone: json["isDone"],
        createdAt: DateTime.parse(json["createdAt"]),
        listJumlah: json["listJumlah"],
        namaDokter: json["namaDokter"],
        namaPasien: json["namaPasien"],
        namaApoteker: json["namaApoteker"],
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "isDone": isDone,
        "createdAt": DateFormat('yyyy-MM-dd HH:mm').format(createdAt),
        "listJumlah": listJumlah,
        "namaDokter": namaDokter,
        "namaPasien": namaPasien,
        "namaApoteker": namaApoteker,
      };
}
