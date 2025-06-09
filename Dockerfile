# Menggunakan image Java yang sesuai
FROM openjdk:17-jdk-slim

# Menentukan direktori kerja di dalam container
WORKDIR /app

# Menyalin file JAR ke dalam container
COPY target/*.jar app.jar

# Menentukan perintah untuk menjalankan aplikasi Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

# Menjalankan aplikasi pada port 8080
EXPOSE 8080