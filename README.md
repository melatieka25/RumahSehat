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

**Docker Compose (Unix/Linux):**
```
# Pertama, build JAR aplikasi
cd RumahSehatWeb
./gradlew clean build -x test

# Lalu, lakukan deployment. Flag --build dapat digunakan untuk mem-build ulang, dan -d untuk menjalankan dalam detach mode.
docker-compose up --build -d
```

**Docker Compose (Windows):**
```
# Pertama, build JAR aplikasi
cd RumahSehatWeb
.\gradlew.bat clean build -x test

# Lalu, lakukan deployment. Flag --build dapat digunakan untuk mem-build ulang, dan -d untuk menjalankan dalam detach mode.
docker-compose up --build -d
```

## Kontrak

---
**Kontrak Tahap 1 Tugas Akhir (Pembagian peran/tanggung jawab)**

| NPM        | Nama Lengkap | Peran/Tanggung Jawab |
|------------| --- |------------------------------| 
| 2006597216 | Ardelia Syahira Yudiva | Project Manager              |                       
| 2006523400 | Melisa Ayu Angelina | Integration  Tester            |                    
| 1906398894 | Alfred Prasetio | Database (Data Modeling)     |                                              
| 2006464266 | Melati Eka Putri | Gitlab Master                 |                        
| 2006597336 | Faris Haidar Zuhdi | DevOps                       |                                               
---
**Kontrak Tahap 2 Tugas Akhir**

| NPM        | Nama Lengkap | Fitur yang akan diselesaikan |
|------------| --- |------------------------------| 
| 2006597216 | Ardelia Syahira Yudiva | 1, 2, 3 |                       
| 2006523400 | Melisa Ayu Angelina | 9, 10, 11 |                    
| 1906398894 | Alfred Prasetio | 6, 7, 8 |                                              |
| 2006464266 | Melati Eka Putri | 4, 5  |                        
| 2006597336 | Faris Haidar Zuhdi | 14, 15 |                                               |
---
**Kontrak Tahap 3 Tugas Akhir**

| NPM        | Nama Lengkap | Fitur yang akan diselesaikan |
|------------| --- |------------------------------|
| 2006597216 | Ardelia Syahira Yudiva | 18                           |                       
| 2006523400 | Melisa Ayu Angelina | 17                           |                    
| 1906398894 | Alfred Prasetio | 12,13                        |                                              |
| 2006464266 | Melati Eka Putri | 16                           |                        
| 2006597336 | Faris Haidar Zuhdi | 21                           |

## Dokumentasi API RumahSehat
Base URL: `http://<domain>/api/v1`

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
    "listAppointment": []
}
```
