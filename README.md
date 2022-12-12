# Tugas Akhir - RumahSehat
## Authors
* **Ardelia Syahira Yudiva** - *2006597216* - *C*
* **Melisa Ayu Angelina** - *2006523400* - *C*
* **Alfred Prasetio** - *1906398894* - *C*
* **Melati Eka Putri** - *2006464266* - *C*
* **Faris Haidar Zuhdi** - *2006597336* - *C*

---
## RumahSehat Web App Deployment
Untuk men-*deploy* aplikasi ini, terdapat dua cara yang direkomendasikan, yaitu langsung melalui `gradle` (untuk keperluan *debugging*), dan menggunakan Docker.

**Gradle (tanpa Docker, Unix/Linux):**
```
cd RumahSehatWeb
./gradlew bootRun
```

**Gradle (tanpa Docker, Windows):**
```
cd RumahSehatWeb
.\gradlew.bat bootRun
```

Sebelum men-*deploy* aplikasi dengan Docker Compose pada server Kirti (mengingat server melakukan *mapping* port 80 ke port aplikasi sesungguhnya berdasarkan subdomain), diperlukan konfigurasi nginx agar port pada *header* `Host` aplikasi benar. Untuk itu, dapat diubah pada `nginx/nginx.conf`, untuk *location* `/` dan `/api/v1/`, diubah *line* berikut:

```
proxy_set_header   Host $host:10090;
```

menjadi

```
proxy_set_header   Host $host:80;
```

Selain itu, variabel `CLIENT_BASE_URL` pada `TA_C_SHA_90.RumahSehatWeb.Setting.Setting.java` juga perlu diubah menjadi URL aplikasi saat sudah di-*deploy*.

**Docker Compose (Unix/Linux):**
```
# Pertama, build JAR kedua aplikasi
./build-all.sh

# Lalu, lakukan deployment. Flag --build dapat digunakan untuk mem-build ulang, dan -d untuk menjalankan dalam detach mode.
docker-compose up --build -d
```

**Docker Compose (Windows):**
```
# Pertama, build JAR kedua aplikasi
.\build-all.bat

# Lalu, lakukan deployment. Flag --build dapat digunakan untuk mem-build ulang, dan -d untuk menjalankan dalam detach mode.
docker-compose up --build -d
```

## Development
Untuk dapat menggunakan fungsionalitas login SSO, ketika men-*develop* aplikasi ini, ubah variabel konfigurasi `CLIENT_BASE_URL` pada `TA_C_SHA_90.RumahSehatWeb.Setting` menjadi URL dan port lokal Anda. Sebagai contoh, `http://localhost:8080` dapat digunakan sebagai nilai sementara selama men-*develop*. Pastikan bahwa Anda mengubah kembali nilai tersebut sebelum melakukan *push* ke Gitlab, agar fungsionalitas login SSO pada aplikasi yang ter-*deploy* dapat berjalan dengan baik. 

## Code Analysis with SonarQube
Project SonarQube untuk *project* ini terdapat pada https://sonarqube.cs.ui.ac.id/dashboard?id=melati.eka_ta_c_sha_90_AYUApPQ8WK4E6S_2LNIo. 

Untuk menggunakan analisis kode dengan SonarQube, kita dapat menggunakan *command* dengan *credentials* berikut pada masing-masing *project* `gradle` (`RumahSehatWeb` dan `RumahSehatAPI`).

**Linux:**
```
./gradlew sonarqube \
  -Dsonar.projectKey=melati.eka_ta_c_sha_90_AYUApPQ8WK4E6S_2LNIo \
  -Dsonar.host.url=https://sonarqube.cs.ui.ac.id \
  -Dsonar.login=454e39959b5a22373641518beb26cd80151b8a93
```

**Windows:**
```
.\gradlew.bat sonarqube -D "sonar.projectKey=melati.eka_ta_c_sha_90_AYUApPQ8WK4E6S_2LNIo" -D "sonar.host.url=https://sonarqube.cs.ui.ac.id" -D "sonar.login=454e39959b5a22373641518beb26cd80151b8a93"
```

## Kontrak

---
**Kontrak Tahap 1 Tugas Akhir (Pembagian peran/tanggung jawab)**

| NPM        | Nama Lengkap | Peran/Tanggung Jawab |
|------------| --- |-------------------------------| 
| 2006597216 | Ardelia Syahira Yudiva | Project Manager |                       
| 2006523400 | Melisa Ayu Angelina | Integration  Tester |                    
| 1906398894 | Alfred Prasetio | Database (Data Modeling) |                                              
| 2006464266 | Melati Eka Putri | Gitlab Master |                        
| 2006597336 | Faris Haidar Zuhdi | DevOps |                                               
---
**Kontrak Tahap 2 Tugas Akhir**

| NPM        | Nama Lengkap | Fitur yang akan diselesaikan |
|------------| --- |------------------------------| 
| 2006597216 | Ardelia Syahira Yudiva | 1, 2, 3 |                       
| 2006523400 | Melisa Ayu Angelina | 9, 10, 11 |                    
| 1906398894 | Alfred Prasetio | 6, 7, 8 |
| 2006464266 | Melati Eka Putri | 4, 5  |                        
| 2006597336 | Faris Haidar Zuhdi | 12, 13 |
---
**Kontrak Tahap 3 Tugas Akhir**

| NPM        | Nama Lengkap | Fitur yang akan diselesaikan |
|------------| --- |------------------------------|
| 2006597216 | Ardelia Syahira Yudiva | 18 |                       
| 2006523400 | Melisa Ayu Angelina | 17, 18, 19 |                    
| 1906398894 | Alfred Prasetio | 14, 18, 19 |
| 2006464266 | Melati Eka Putri | 16, 18, 19 |                        
| 2006597336 | Faris Haidar Zuhdi | 15, 18, 19, 22, 23 |

## Dokumentasi API RumahSehat
Base URL: `http://<domain>/api/v1`. Semua *request* terhadap *endpoints* berikut, kecuali untuk autentikasi, memerlukan *bearer token* yang didapatkan dari *endpoint* autentikasi.

Dokumentasi API juga tersedia pada https://ristek.link/DokumentasiAPI-TK-APAP-90.

### createAuthenticationToken
---
**Method**: `POST` \
**Endpoint**: `/authenticate` \
Mengautentikasi pasien dan mengembalikan *bearer token*.

#### Body Params
```
{
    "username": "suatuUsernameDariSeorangUser",
    "password": "passwordIniDalamPlaintextYuh"
}
```

#### Success Response
```
{
    "token": "iniBukanTokenBeneran"
}
```

### retrievePasien
---
**Method**: `GET` \
**Endpoint**: `/pasien/{uuid}` \
Mengembalikan suatu pasien berdasarkan UUID.

#### Success Response
```
{
    "uuid": "4028b88184956e9001849576ab460002",
    "nama": "Pasien01",
    "role": "Role",
    "username": "pasien.satu",
    "password": "someHashedPassword",
    "email": "pasien@satu.com",
    "saldo": 1337420,
    "umur": 42,
    "isSso": false,
    "listAppointment": []
}
```

### retrieveAllPasien
---
**Method**: `GET` \
**Endpoint**: `/pasien` \
Mengembalikan daftar pasien.

#### Success Response
```
[
    {
        "uuid": "4028b88184956e9001849576ab460002",
        "nama": "Pasien01",
        "role": "Role",
        "username": "pasien.satu",
        "password": "someHashedPassword",
        "email": "pasien@satu.com",
        "saldo": 1337420,
        "umur": 42,
        "isSso": false,
        "listAppointment": []
    },
    {
        "uuid": "4028b88184956e9001849576ab460004",
        "nama": "Pasien03",
        "role": "Role",
        "username": "pasien.tiga",
        "password": "lhoPasienDuanyaKeManaDong?",
        "email": "pasien@tiga.com",
        "saldo": 8213892,
        "umur": 16,
        "isSso": false,
        "listAppointment": []
    }
]
```

### createPasien
---
**Method**: `POST` \
**Endpoint**: `/pasien/new` \
Menambahkan suatu pasien baru.

#### Body Params
```
{
    "nama": "Pasien04",
    "role": "Role",
    "username": "pasien.empat",
    "password": "bantuSayaCariPasienDua!!",
    "email": "pasien@empat.com",
    "saldo": 101010101,
    "umur": 101,
    "isSso": false,
    "listAppointment": []
}
```

#### Success Response
```
{
    "uuid": "4028b88184956e9001849576ab460005",
    "nama": "Pasien04",
    "role": "Role",
    "username": "pasien.empat",
    "password": "bantuSayaCariPasienDua!!",
    "email": "pasien@empat.com",
    "saldo": 101010101,
    "umur": 101,
    "isSso": false,
    "listAppointment": []
}
```

### deletePasien
---
**Method**: `DELETE` \
**Endpoint**: `/pasien/{uuid}` \
Menghapus suatu pasien berdasarkan UUID.

#### Success Response
```
Pasien with UUID 4028b88184956e9001849576ab460003 has been deleted successfully
```

### updatePasien
---
**Method**: `PUT` \
**Endpoint**: `/pasien/{uuid}` \
Mengubah *field(s)* dari suatu pasien berdasarkan UUID.

#### Body Params
```
{
    "nama": "Pasien04",
    "role": "Role",
    "username": "pasien.empat",
    "password": "owalahTernyataPasienDuaDihapusGan",
    "email": "pasien@empat.com",
    "saldo": 101010101,
    "umur": 101,
    "isSso": false,
    "listAppointment": []
}
```

#### Success Response
```
{
    "uuid": "4028b88184956e9001849576ab460005",
    "nama": "Pasien04",
    "role": "Role",
    "username": "pasien.empat",
    "password": "owalahTernyataPasienDuaDihapusGan",
    "email": "pasien@empat.com",
    "saldo": 101010101,
    "umur": 101,
    "isSso": false,
    "listAppointment": []
}
```

### updateSaldo
---
**Method**: `POST` \
**Endpoint**: `/pasien/saldo` \
Mengubah (menambahkan) *saldo* dari suatu pasien berdasarkan *username* pada *request body*. Contoh berikut menambahkan Rp750 ke saldo pasien.

#### Body Params
```
{
    "username": "SeorangPasien",
    "saldo": 750
}
```

#### Success Response
```
Saldo for pasien SeorangPasien has been updated successfully
```

### retrieveProfil
---
**Method**: `GET` \
**Endpoint**: `/pasien/profil/{username}` \
Mengembalikan profil pasien berdasarkan *username*, tidak seperti `retrievePasien` yang mengambil berdasarkan UUID.

#### Success Response
```
{
    "uuid": "4028b88184956e9001849576ab460002",
    "nama": "Pasien01",
    "role": "Role",
    "username": "pasien.satu",
    "password": "someHashedPassword",
    "email": "pasien@satu.com",
    "saldo": 1337420,
    "umur": 42,
    "isSso": false,
    "listAppointment": []
}
```

### retrieveAllTagihanByUsername
---
**Method**: `GET` \
**Endpoint**: `/tagihan/{username}` \
Mengembalikan semua tagihan dari seorang *user* berdasarkan usernamenya.

#### Success Response
```
{
  "listTagihan": [
    {
      "kode": "BILL-1",
      "tanggalTerbuat": "2022-12-11T13:34:52",
      "tanggalBayar": "2022-12-11T13:35:30",
      "isPaid": true,
      "jumlahTagihan": 100000,
      "appointment": {
        "kode": "APT-1",
        "waktuAwal": "2022-12-11 20:32",
        "isDone": true,
        "resep": null,
        "namaDokter": null,
        "namaPasien": null
      }
    },
    {
      "kode": "BILL-3",
      "tanggalTerbuat": "2022-12-11T14:25:09",
      "tanggalBayar": "2022-12-11T14:25:51",
      "isPaid": true,
      "jumlahTagihan": 100000,
      "appointment": {
        "kode": "APT-6",
        "waktuAwal": "2022-12-13 02:24",
        "isDone": true,
        "resep": null,
        "namaDokter": null,
        "namaPasien": null
      }
    }
  ]
}
```

### payTagihanByKode
---
**Method**: `GET` \
**Endpoint**: `/tagihan/{username}/bayar/{kode}` \
Membayar tagihan berdasarkan username dan kode tagihan serta mengembalikan status pembayaran.

#### Success Response
```
{
  "statusPembayaran": true
}
```

### retrieveResep
---
**Method**: `GET` \
**Endpoint**: `/resep/{id}` \
Mengembalikan suatu resep berdasarkan ID.

#### Success Response
```
{
  "createdAt": "2022-12-10T18:18:10",
  "namaDokter": "Dokter",
  "id": 1,
  "namaApoteker": "Apoteker",
  "isDone": true,
  "listJumlah": [
    {
      "kuantitas": 2,
      "namaObat": "Alum Hydrox_Cap 475mg"
    },
    {
      "kuantitas": 10,
      "namaObat": "Alu-Cap_Cap 475mg"
    }
  ],
  "namaPasien": "Pasien"
}
```

### retrieveAllAppointment
---
**Method**: `GET` \
**Endpoint**: `/appointment/{username}` \
Mengembalikan semua *appointment* berdasarkan *username*.

#### Success Response
```
{
  "listAppointment": [
    {
      "kode": "APT-1",
      "waktuAwal": "2022-12-11 20:32",
      "isDone": true,
      "resep": null,
      "namaDokter": "Dokter",
      "namaPasien": "pasien"
    },
    {
      "kode": "APT-6",
      "waktuAwal": "2022-12-13 02:24",
      "isDone": true,
      "resep": null,
      "namaDokter": "Dokter",
      "namaPasien": "pasien"
    }
  ]
}
```

### createAppointment
---
**Method**: `POST` \
**Endpoint**: `/appointment/create` \
Membuat suatu *appointment* baru.

#### Body Params
```
{
    "kode": null,
    "waktuAwal": "2022-12-12 17:00:00",
    "isDone": false,
    "resep": null,
    "tagihan": null,
    "pasien": "pasien",
    "dokter": "sayadokter",
    "namaDokter": null,
    "namaPasien": null
}
```

### getAllDokter
---
**Method**: `GET` \
**Endpoint**: `/dokter` \
Mengembalikan semua *dokter*.

#### Success Response
```
[
    {
        "uuid": "402890848505e1cb0185060001bb0000",
        "nama": "Dokter Butuh Uang",
        "role": "Dokter",
        "username": "dokter.mau.uang",
        "password": "$2a$10$9htYN0DYUoAxqKgUhG8iLec6eMvAjIW2wQlEhwU2V57ULC/ImxCbu",
        "email": "dokter.mau.duit@protonmail.ch",
        "isSso": false,
        "tarif": 999999999,
        "listAppointment": []
    },
    {
        "uuid": "8ae3068384ec8fd70184ec951d630000",
        "nama": "Seorang Dokter",
        "role": "Dokter",
        "username": "dokter.1",
        "password": "$2a$10$vsZJWnF2zsXiftou1u0yDOUPGcWIu3j8khOn6OQvbBBk3JhUoLUxO",
        "email": "dokter@dokter.com",
        "isSso": false,
        "tarif": 12345,
        "listAppointment": []
    }
]
```