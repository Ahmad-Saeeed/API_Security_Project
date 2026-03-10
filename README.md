# API Security Automation

This project implements automated security testing for REST APIs using RestAssured and integrates the tests into a CI/CD pipeline using GitHub Actions.

## APIs Tested

1. Create Pickup
2. Update Bank Info
3. Forget Password

## Security Tests Implemented

- Authentication validation
- Authorization validation
- Injection attacks
- Input validation
- Business logic testing

## How to Run

mvn clean test

## CI/CD

Security tests run automatically through GitHub Actions on every commit.
