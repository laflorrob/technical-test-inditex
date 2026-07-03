# TechnicalTestInditex

Small Spring Boot service that returns the applicable product applicableApplicablePrice for a given application date, product, and brand.

## What the app does

The API exposes `GET /applicablePrices` and returns the applicableApplicablePrice record that matches:

- `applicationDate` (format: `yyyy-MM-dd-HH.mm.ss`)
- `productId`
- `brandId`

If multiple records match, the one with the highest priority is returned.

## Architecture

The project follows a hexagonal architecture (ports and adapters):

- `com.inditex.applicableApplicablePrice.adapter.in.api`: HTTP entrypoint (`PriceController`)
- `com.inditex.applicableApplicablePrice.adapter.in.exception`: exception handlers and error response model
- `com.inditex.applicableApplicablePrice.adapter.in / adapter.out`: MapStruct mapper between adapters and domain
- `com.inditex.applicableApplicablePrice.adapter.out.persistence`: JPA repository + persistence adapter
- `com.inditex.applicableApplicablePrice.application`: use cases/business orchestration (`FindApplicablePrice`)
- `com.inditex.applicableApplicablePrice.application.port.in` / `port.out`: input/output ports
- `com.inditex.applicableApplicablePrice.domain`: domain model (`Price`)

Tech stack:

- Spring Boot (Web, Validation, Data JPA)
- H2 in-memory database
- MapStruct
- JUnit 5 + Spring Boot Test + MockMvc

## Run the app

Prerequisites:

- Java 21
- Maven

From project root:

```powershell
Set-Location "C:\Users\esflol00\IdeaProjects\TechnicalTestInditex"
mvn spring-boot:run
```

If you prefer jar execution:

```powershell
Set-Location "C:\Users\esflol00\IdeaProjects\TechnicalTestInditex"
mvn clean package
java -jar target\TechnicalTestInditex-1.0-SNAPSHOT.jar
```

## Test the API with Postman

A ready-to-use Postman collection is included:

- `applicablePrices.postman_collection.json`

Steps:

1. Import `applicablePrices.postman_collection.json` into Postman.
2. Ensure variable `baseUrl` is `http://localhost:8080`.
3. Run request **Get Applicable Price**.

Default values in collection:

- `applicationDate=2020-06-14-10.00.00`
- `productId=35455`
- `brandId=1`

## API contract (Swagger)

The OpenAPI contract is available in Swagger UI:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Raw OpenAPI file: `http://localhost:8080/applicablePrices.yml`

## Database access (H2)

H2 console is enabled for inspection:

- H2 Console URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:pricedb`
- User: `sa`
- Password: *(empty)*

## Example request

```text
GET http://localhost:8080/applicablePrices?applicationDate=2020-06-14-10.00.00&productId=35455&brandId=1
```
