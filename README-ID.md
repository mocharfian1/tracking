# Tracking Number Generator

Aplikasi ini berfungsi untuk menghasilkan nomor pelacakan unik berdasarkan data negara asal, negara tujuan, dan informasi pelanggan. Nomor pelacakan dihasilkan dengan menambahkan timestamp dan nilai counter yang akan di-reset setiap harinya.

GitLab:
- https://gitlab.com/mocharfian1/tracking

URL:
http://34.30.175.83:8080/next-tracking-number

CURL :
```
curl --location 'http://34.30.175.83:8080/next-tracking-number?originCountryId=ID&destinationCountryId=US&customerId=123e4567-e89b-12d3-a456-426614174000&customerName=sdfee&customerSlug=redbox-logistics&weight=2.5'
```

## Fitur Utama

- Menghasilkan nomor pelacakan dengan format tertentu (contoh: **USID0000000005202306091200**).
- Menyimpan informasi tracking ke database menggunakan **H2**.
- Menangani error menggunakan **GlobalExceptionHandler**.
- Pengujian unit menggunakan **JUnit 4** dan **MockMvc**.
- Deployment otomatis ke **Google Kubernetes Engine (GKE)** menggunakan **GitLab CI/CD** saat terjadi perubahan pada branch `main`.

## Teknologi

- Spring Boot, Spring Data JPA, H2 Database
- JUnit 4, MockMvc
- Java 17
- GitLab CI/CD, Docker
- Google Cloud Platform (GCP), Google Kubernetes Engine (GKE)

## Prasyarat

- Java 17 atau lebih tinggi
- Maven
- Akun dan konfigurasi GCP (untuk deployment)

## Menjalankan Aplikasi Lokal

```bash
git clone https://github.com/username/repository.git
cd repository
mvn clean install
mvn spring-boot:run
```

Aplikasi tersedia di `http://localhost:8080`

### Akses H2 Console

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: `password`

## Endpoint

`GET /next-tracking-number`  
Request body:
```json
{
  "originCountryId": "US",
  "destinationCountryId": "ID",
  "customerId": "customer1",
  "customerName": "Customer Name",
  "customerSlug": "customer-slug",
  "weight": 10.5
}
```

## Deployment Otomatis (CI/CD + GKE)

- Perubahan pada branch `main` akan:
  - Build image Docker
  - Push image ke Google Container Registry
  - Deploy ke GKE menggunakan `kubectl` atau `helm`
- Semua konfigurasi dikelola melalui `.gitlab-ci.yml` dan file manifest Kubernetes.

## Pengujian

```bash
mvn test
```

## Kontribusi

Silakan buat pull request atau buka issue.

## Lisensi

Moch. Arfian Ardiansyah