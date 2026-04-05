# MediGuard Integration Gateway - LK05

## 📌 Deskripsi
Project ini merupakan implementasi konsep Java Generics untuk membangun sebuah sistem integrasi data medis yang aman, fleksibel, dan mudah dikembangkan.

Sistem ini mensimulasikan kasus penggunaan pada PT Asuransi BPJS, di mana berbagai rumah sakit meminta data pasien dalam versi yang berbeda (V1 dan V2), dan data sensitif harus ditangani sesuai level akses.

Project ini dibuat sebagai bagian dari Latihan Kerja 05 (LK05) dengan tujuan menerapkan konsep:
- Generic Class
- Bounded Type Parameters
- Multiple Bounds (`extends A & B & C`)
- Abstraction dan Interface
- Secure Data Masking

## 👥 Anggota Kelompok
| Nama | NIM |
| --- | --- |
| Aryan Zaky Prayogo | 255150207111059 |
| Achmad Hujairi | 255150200111042 |
| M. Hidayatulloh H. A. M | 255150201111025 |
| M. Ahshal Zilhamsyah | 255150200111041 |
| Dikardo Siahaan | 255150200111040 |

## 🧩 Pembagian Tugas
| Anggota | Tugas |
| --- | --- |
| M. Hidayatulloh H. A. M | Desain interface generik `MedicalRecord`, `Versioned`, `Confidential` dan kontrak `SecureResponse<T>` (harus dibuat dulu) |
| Achmad Hujairi | Implementasi data `PatientProfileV1` dan `PatientProfileV2`, serta mekanisme masking data sensitif |
| M. Ahshal Zilhamsyah | Implementasi `IntegrationGateway<T>` dengan logika `fetchData(...)` dan validasi level keamanan |
| Aryan Zaky Prayogo | Buat kelas `MainSimulation` / `AppTun` untuk mensimulasikan permintaan data dokter akses rendah/tinggi |
| Dikardo Siahaan | Berkolaborasi dengan Hidayatulloh membuat kelas pendukung, pengujian sistem, review kode, dokumentasi, screenshot hasil, dan penyusunan penjelasan |

> Catatan: setiap anggota juga bertanggung jawab untuk review kode, dokumentasi, screenshot hasil, dan menyusun penjelasan singkat untuk bagian tugasnya.

## 🏗 Struktur Program
LK05
├── MedicalRecord.java
├── Versioned.java
├── Confidential.java
├── PatientProfileV1.java
├── PatientProfileV2.java
├── SecureResponse.java
├── IntegrationGateway.java
└── MainSimulation.java

## ⚙️ Konsep Java Generics yang Digunakan
1️⃣ **Bounded Types**
- `T extends MedicalRecord & Versioned & Confidential`
- Memastikan objek data medis punya ID, versi, dan level keamanan.

2️⃣ **Multiple Bounds**
- Menggunakan `&` untuk menggabungkan beberapa interface.
- Contoh: `SecureResponse<T extends MedicalRecord & Confidential>`.

3️⃣ **Interface dan Abstraction**
- Gateway bekerja dengan objek data yang memenuhi kontrak interface.
- Masking dijalankan melalui interface `Confidential`, bukan `instanceof`.

4️⃣ **Data Masking Otomatis**
- Objek data bertanggung jawab untuk menyamarkan field sensitif.
- `IntegrationGateway` hanya memanggil `maskSensitiveData()` bila perlu.

## 🛡️ Fitur Program
- Integrasi data medis generik dengan versi berbeda (V1, V2).
- Wrapper respons `SecureResponse<T>` dengan pengecekan keamanan.
- Masking otomatis untuk data sensitif saat level akses rendah.
- Dapat diperluas dengan tipe data baru tanpa mengubah gateway inti.
- Simulasi permintaan data menggunakan satu gateway generik.

## 🔄 Mekanisme Program
1. Inisialisasi data pasien V1 dan V2.
2. Buat instance `IntegrationGateway` untuk tiap data.
3. Panggil `fetchData(patientId, requesterClearanceLevel)`.
4. Gateway mengecek level akses.
5. Jika level akses kurang, panggil `maskSensitiveData()` pada objek.
6. Bungkus hasil ke `SecureResponse`.
7. Tampilkan hasil ke console dengan status keamanan.

## 📌 Catatan
- Pastikan tidak ada `if (data instanceof ...)` dalam `IntegrationGateway`.
- Tambahan tipe data baru seperti `ClaimHistoryV1` cukup dengan membuat kelas baru yang implement interface yang sesuai.
- Dokumentasikan screenshot hasil console dan berikan penjelasan singkat tentang akses rendah vs tinggi.

## 📅 Deadline
08 April 2026, jam 17.00 WIB

