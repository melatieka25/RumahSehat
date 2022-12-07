import 'dart:convert';

AllTagihan AllTagihanFromJson(String str) => AllTagihan.fromJson(json.decode(str));

String AllTagihanToJson(AllTagihan data) => json.encode(data.toJson());

class AllTagihan {
  AllTagihan({
    required this.listTagihan,
  });

  List<Tagihan> listTagihan;

  factory AllTagihan.fromJson(Map<String, dynamic> json) => AllTagihan(
    listTagihan: List<Tagihan>.from(json["listTagihan"].map((x) => Tagihan.fromJson(x))),
  );

  Map<String, dynamic> toJson() => {
    "listTagihan": listTagihan[0].toJson(),
  };
}

class Tagihan {
  Tagihan(
      { required this.kode,
        required this.tanggalTerbuat,
        required this.isPaid,
      });

  String kode;
  String tanggalTerbuat;
  bool isPaid;

  factory Tagihan.fromJson(Map<String, dynamic> json) => Tagihan(
    kode: json["kode"],
    tanggalTerbuat: json["tanggalTerbuat"],
    isPaid: json["isPaid"],
  );

  Map<String, dynamic> toJson() => {
    "kode": kode,
    "tanggalTerbuat": tanggalTerbuat,
    "isDone": isPaid,
  };
}
