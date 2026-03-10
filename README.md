# API Security Automation Project

## Overview

This project implements automated **API security testing** using **Java, REST Assured, and TestNG**.
The goal is to validate the security posture of several APIs by testing authentication, input validation, injection attacks, and rate limiting.

The project also integrates **CI/CD automation using GitHub Actions** to run the security tests automatically on every commit and nightly schedule.

---

## Technologies Used

* **Java 8**
* **REST Assured**
* **TestNG**
* **Maven**
* **GitHub Actions (CI/CD)**

---

## APIs Tested

| API              | Endpoint                           | Purpose                                |
| ---------------- | ---------------------------------- | -------------------------------------- |
| Create Pickup    | `/api/v2/pickups`                  | Creates pickup requests for businesses |
| Update Bank Info | `/api/v2/businesses/add-bank-info` | Updates bank details for cashouts      |
| Forget Password  | `/api/v2/users/forget-password`    | Sends password reset link              |

---

## Security Tests Implemented

The following security checks are automated:

### Authentication Testing

* Missing Authorization Token
* Invalid Authorization Token

### Input Validation

* Invalid email format
* Missing required fields
* Malformed JSON

### Injection Testing

* SQL Injection
* Script Injection (XSS)

### API Abuse Protection

* Rate limiting verification (`429 Too Many Requests`)

### Server Stability

* Ensure API does **not return 500 errors** when receiving malicious input.

---

## Project Structure

```
api-security-automation
│
├── pom.xml
├── testng.xml
├── security-report.json
├── README.md
│
├── src
│   └── test
│       └── java
│           ├── security
│           │   ├── PickupSecurityTest.java
│           │   ├── BankInfoSecurityTest.java
│           │   └── ForgetPasswordSecurityTest.java
│           │
│           └── utils
│               └── AuthUtil.java
│
└── .github
    └── workflows
        └── api-security.yml
```

---

## Running Tests Locally

Run all tests:

```
mvn clean test
```

Generate HTML report:

```
mvn surefire-report:report
```

Report location:

```
target/site/surefire-report.html
```

---

## CI/CD Pipeline

Security tests run automatically using **GitHub Actions**:

### Triggered On

* Every push to `main`
* Nightly scheduled run (2 AM)
* Manual workflow execution

### Pipeline Steps

1. Checkout repository
2. Setup Java
3. Build project
4. Run security tests
5. Generate test reports
6. Upload artifacts

---

## Security Scan Report

After each run, the pipeline generates a **security report**:

```
security-report.json
```

Example:

```
{
  "scanDate": "2026-03-10",
  "apisTested": 3,
  "testsExecuted": 9,
  "status": "Completed"
}
```

---

## Important Note

Some APIs require valid production data (OTP, verified business accounts).
Therefore, tests focus on **security validation rather than functional success**, ensuring APIs **do not crash or expose vulnerabilities** when receiving malicious inputs.

---

## Author

Ahmed with ChatGPT lot of assistance 
