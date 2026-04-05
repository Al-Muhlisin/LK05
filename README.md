# MediGuard Integration Gateway - LK05

## 📌 Deskripsi
Proyek ini adalah implementasi **Java Generics** untuk studi kasus integrasi data medis PT Asuransi BPJS.

Masalah yang diselesaikan:
1. Perbedaan format data antar rumah sakit (V1 dan V2).
2. Kebocoran data sensitif ketika clearance requester tidak mencukupi.
3. Duplikasi endpoint/logika backend yang rapuh.

Solusi yang dibangun adalah satu jalur gateway generik yang aman, fleksibel, dan mudah diperluas.

---

## 👥 Anggota Kelompok
| Nama | NIM |
| --- | --- |
| Aryan Zaky Prayogo | 255150207111059 |
| Achmad Hujairi | 255150200111042 |
| M. Hidayatulloh H. A. M | 255150201111025 |
| M. Ahshal Zilhamsyah | 255150200111041 |
| Dikardo Siahaan | 255150200111040 |

---

## 🧩 Mapping Task Anggota 

| Anggota | Fokus Tanggung Jawab | File Utama |
| --- | --- | --- |
| M. Hidayatulloh H. A. M | Desain kontrak inti dan generic constraints | `MedicalRecord.java`, `Versioned.java`, `Confidential.java`, `SecureResponse.java` |
| Achmad Hujairi | Implementasi DTO versi data + masking spesifik objek | `PatientProfileV1.java`, `PatientProfileV2.java` |
| M. Ahshal Zilhamsyah | Service generic gateway + logika akses | `IntegrationGateway.java` |
| Aryan Zaky Prayogo | Simulasi aplikasi (interaktif + demo otomatis) | `MainSimulation.java`, `AppTun.java` |
| Dikardo Siahaan | Integrasi akhir, validasi compile/run, dokumentasi dan perapihan repo | `README.md`, `.gitignore` |

Tambahan kolaboratif semua anggota:
- Code review lintas file
- Pengujian alur akses rendah vs tinggi
- Penyusunan bukti hasil console

---

## 🏗 Struktur Program
```text
LK05/
├── AppTun.java
├── MainSimulation.java
├── MedicalRecord.java
├── Versioned.java
├── Confidential.java
├── SecureResponse.java
├── PatientProfileV1.java
├── PatientProfileV2.java
└── IntegrationGateway.java
```

---

## ⚙️ Implementasi Requirement Teknis

### A. Struktur Data Berjenjang (Bounded Types)
1. `MedicalRecord` sebagai kontrak data medis dasar (`getPatientId()`).
2. `Versioned` menyediakan `getVersion()`.
3. Semua DTO mengimplementasikan `MedicalRecord`, `Versioned`, `Confidential`.
4. Tersedia 2 DTO:
	- `PatientProfileV1`
	- `PatientProfileV2` (memiliki field tambahan seperti alergi).

### B. Generic API Response dengan Keamanan
1. `SecureResponse<T extends MedicalRecord & Confidential>` digunakan untuk membungkus hasil.
2. Mekanisme masking berjalan otomatis melalui pemanggilan `maskSensitiveData()`.
3. `Confidential` menyediakan:
	- konstanta level: `PUBLIC`, `RESTRICTED`, `SECRET`
	- method `getSecurityLevel()`
	- method `maskSensitiveData()`

### C. Generic Gateway Service
`IntegrationGateway<T extends MedicalRecord & Versioned & Confidential>` memiliki method:
- `fetchData(String patientId, int requesterClearanceLevel)`

Alur bisnis:
1. Validasi `patientId`.
2. Bandingkan `requesterClearanceLevel` dengan `data.getSecurityLevel()`.
3. Jika clearance lebih rendah → panggil `data.maskSensitiveData()`.
4. Kembalikan hasil sebagai `SecureResponse<T>`.

> Tidak ada hardcoding tipe (`instanceof`) di gateway.

### D. Ekstensibilitas
Untuk menambah jenis data baru (mis. `ClaimHistoryV1`):
1. Buat kelas baru.
2. Implement `MedicalRecord`, `Versioned`, `Confidential`.
3. Langsung bisa dipakai pada `IntegrationGateway<T>` tanpa ubah kode inti gateway.

---

## 🛡️ Poin Penting Generic & Keamanan
- **Multiple bounds**: `T extends A & B & C`
- **Compile-time safety**: minim risiko salah tipe dan tanpa casting manual di logika utama.
- **Tell, Don’t Ask**: gateway menyuruh objek memasking dirinya sendiri.
- **DRY**: V1 dan V2 diproses oleh service generic yang sama.

---

## 🔄 Mekanisme Simulasi (Main)
Di `MainSimulation` / `AppTun`:
1. Inisialisasi data V1 dan V2.
2. Buat gateway untuk masing-masing data.
3. Simulasi 2 skenario:
	- akses rendah (data sensitif dimasking)
	- akses tinggi (data tampil penuh)
4. Jalankan juga mode interaktif menggunakan `Scanner`.

---

## ▶️ Cara Menjalankan
1. Compile:
	- `javac *.java`
2. Run:
	- `java AppTun`
	- atau `java MainSimulation`

---

## ✅ Checklist Rubrik Penilaian
1. **Compile-Time Safety (20%)**
	- Kompilasi sukses.
	- Tidak ada casting manual pada logika bisnis utama.
2. **Implementasi Generic Bound (25%)**
	- Menggunakan bounded generic dan multiple bounds.
3. **Keamanan Data/Masking (30%)**
	- Masking otomatis berdasarkan clearance level, tanpa hardcode tipe di gateway.
4. **Prinsip DRY (15%)**
	- Satu gateway untuk V1/V2.
5. **Kelengkapan Fitur (10%)**
	- Ada minimal dua versi data + simulasi output benar.

---

## 📌 Catatan 
- Sertakan screenshot output:
  - V1 akses rendah vs tinggi
  - V2 akses rendah vs tinggi
- Jelaskan singkat kenapa data tertentu dimasking pada clearance rendah.

