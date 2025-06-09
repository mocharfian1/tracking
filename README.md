# Tracking Number Generator

This application generates a unique tracking number based on the origin country, destination country, and customer data. The tracking number includes a timestamp and a daily-reset counter.

GitLab:
- https://gitlab.com/mocharfian1/tracking

URL:
http://34.30.175.83:8080/next-tracking-number

CURL :
```
curl --location 'http://34.30.175.83:8080/next-tracking-number?originCountryId=ID&destinationCountryId=US&customerId=123e4567-e89b-12d3-a456-426614174000&customerName=sdfee&customerSlug=redbox-logistics&weight=2.5'
```

## Key Features

- Generates tracking numbers in a specific format (e.g., **USID0000000005202306091200**).
- Saves tracking info to a **H2** in-memory database.
- Global error handling with **GlobalExceptionHandler**.
- Unit testing with **JUnit 4** and **MockMvc**.
- Automatic deployment to **Google Kubernetes Engine (GKE)** via **GitLab CI/CD** on `main` branch updates.

## Technologies

- Spring Boot, Spring Data JPA, H2 Database
- JUnit 4, MockMvc
- Java 17
- GitLab CI/CD, Docker
- Google Cloud Platform (GCP), Google Kubernetes Engine (GKE)

## Prerequisites

- Java 17 or higher
- Maven
- GCP account and configuration (for deployment)

## Running Locally

```bash
git clone https://github.com/username/repository.git
cd repository
mvn clean install
mvn spring-boot:run
```

App runs at `http://localhost:8080`

### H2 Console Access

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

## Automatic Deployment (CI/CD + GKE)

- Any push to the `main` branch triggers:
  - Docker image build
  - Image push to Google Container Registry
  - Deployment to GKE using `kubectl` or `helm`
- Configuration is handled via `.gitlab-ci.yml` and Kubernetes manifests.

## Testing

```bash
mvn test
```

## Contribution

Feel free to open an issue or submit a pull request.

## License

Moch. Arfian Ardiansyah