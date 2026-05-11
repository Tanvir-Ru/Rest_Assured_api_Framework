# 🟡 rest-assured-api-framework

> **Level: Medium** | Language: Java | Framework: Rest Assured 5.x | Runner: TestNG | Reporting: ExtentReports

A professional REST API test automation framework using Rest Assured with Java, featuring POJO serialization, JSON Schema validation, OAuth2 authentication, and chained request scenarios.

![CI](https://github.com/Tanvir-Ru/rest-assured-api-framework/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-11+-ED8B00?logo=java)
![RestAssured](https://img.shields.io/badge/RestAssured-5.x-339933)
![TestNG](https://img.shields.io/badge/TestNG-7.x-FF6B6B)

---

## 📁 Project Structure

```
rest-assured-api-framework/
├── src/
│   ├── main/java/com/tanvir/
│   │   ├── config/
│   │   │   ├── ConfigManager.java     # Env config loader
│   │   │   └── RestAssuredConfig.java # Base URI / filters
│   │   ├── models/
│   │   │   ├── User.java              # POJO — User
│   │   │   ├── Loan.java              # POJO — Loan
│   │   │   └── ApiResponse.java       # Generic wrapper
│   │   └── utils/
│   │       ├── AuthHelper.java        # JWT / OAuth2
│   │       ├── SchemaValidator.java   # JSON Schema validation
│   │       └── TestDataGenerator.java
│   └── test/java/com/tanvir/
│       ├── tests/
│       │   ├── BaseTest.java
│       │   ├── UserApiTest.java
│       │   ├── LoanApiTest.java
│       │   └── AuthApiTest.java
│       └── endpoints/
│           ├── UserEndpoints.java
│           └── LoanEndpoints.java
├── test-data/
│   ├── schemas/
│   │   ├── user-schema.json
│   │   └── loan-schema.json
│   └── testdata.json
├── .github/workflows/
│   └── ci.yml
├── testng.xml
├── pom.xml
├── .env.example
└── README.md
```

---

## 🛠️ Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 11+ | Language |
| Rest Assured | 5.x | HTTP client & DSL |
| TestNG | 7.x | Test runner |
| Jackson | 2.x | JSON ↔ POJO mapping |
| Everit JSON Schema | — | JSON Schema validation |
| ExtentReports | 5.x | HTML test reports |
| Maven | 3.x | Build & deps |

---

## ⚙️ Setup

```bash
git clone https://github.com/Tanvir-Ru/rest-assured-api-framework.git
cd rest-assured-api-framework

# Run all tests
mvn clean test

# Run specific group
mvn clean test -Dgroups=smoke

# Run with report
mvn clean test && open target/extent-reports/index.html
```

---

## 💻 Sample — BDD Given/When/Then

```java
given()
    .header("Authorization", "Bearer " + token)
    .contentType(ContentType.JSON)
    .body(new User("alice@qa.com", "Alice"))
.when()
    .post("/api/users")
.then()
    .statusCode(201)
    .body("email", equalTo("alice@qa.com"))
    .body("id", notNullValue());
```

---

## 🧑‍💻 Author

**Tanvir Hossain** — Senior QA Engineer
📧 iamtanvir.cse@gmail.com | 🔗 [LinkedIn](https://linkedin.com/in/tanvir-hossain)
