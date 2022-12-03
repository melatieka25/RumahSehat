// To parse this JSON data, do
//
//     final tesCovid = tesCovidFromJson(jsonString);

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
    listResep: List<Resep>.from(json["listResep"].map((x) => Resep.fromJson(x))),
  );

  Map<String, dynamic> toJson() => {
    "listPasien": listResep[0].toJson(),
  };
}

class Resep {
  Resep(
      { required this.id,
        required this.isDone,
        required this.createdAt,
        required this.listJumlah,
      });
  //TODO: yang perlu diganti paling listJumlah, tambah apapun bebas
  int id;
  bool isDone;
  DateTime createdAt;
  List listJumlah;


  factory Resep.fromJson(Map<String, dynamic> json) => Resep(
    id: json["id"],
    isDone: json["isDone"],
    createdAt: DateTime.parse(json["createdAt"]),
    listJumlah: json["listJumlah"],
  );

  Map<String, dynamic> toJson() => {
    "id" : id,
    "isDone": isDone,
    "createdAt": DateFormat('yyyy-MM-dd HH:mm').format(createdAt),
    "listJumlah": listJumlah,
  };
}


