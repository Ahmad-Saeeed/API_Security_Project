# API Security Test Cases

## Overview

This document describes the security testing approach applied to the following APIs:

1. Create Pickup API
2. Update Bank Info API
3. Forget Password API

The tests were designed following the OWASP API Security Top 10 guidelines.

---

# 1. Authentication Tests

### TC-AUTH-01 Missing Authorization Token
API: Create Pickup / Update Bank Info

Steps:
Send request without Authorization header.

Expected Result:
API returns 401 Unauthorized.

---

### TC-AUTH-02 Invalid Token
API: Create Pickup / Update Bank Info

Steps:
Send request with malformed token.

Expected Result:
API returns 401 Unauthorized.

---

### TC-AUTH-03 Expired Token
API: Update Bank Info

Steps:
Use expired JWT token.

Expected Result:
API rejects request with 401 Unauthorized.

---

# 2. Authorization Tests

### TC-AUTHZ-01 Broken Object Level Authorization
API: Create Pickup

Steps:
Modify businessLocationId to another business.

Expected Result:
API should reject request with 403 Forbidden.

---

### TC-AUTHZ-02 Role Escalation
API: Update Bank Info

Steps:
Modify JWT payload role to ADMIN.

Expected Result:
Request rejected with 403.

---

# 3. Input Validation Tests

### TC-VAL-01 Invalid Email Format
API: Forget Password

Input:
email="invalid_email"

Expected Result:
API returns validation error (400).

---

### TC-VAL-02 SQL Injection
API: Create Pickup

Input:
name="' OR 1=1 --"

Expected Result:
Request rejected and server should not return 500.

---

### TC-VAL-03 Cross Site Scripting (XSS)
API: Update Bank Info

Input:
beneficiaryName="<script>alert(1)</script>"

Expected Result:
Input sanitized or rejected.

---

### TC-VAL-04 Large Payload Attack

Steps:
Send payload with 10,000 characters.

Expected Result:
API rejects request with 413 Payload Too Large.

---

# 4. Business Logic Tests

### TC-BUS-01 OTP Verification
API: Update Bank Info

Steps:
Send incorrect OTP.

Expected Result:
Request rejected.

---

### TC-BUS-02 OTP Brute Force
API: Update Bank Info

Steps:
Attempt multiple OTP values quickly.

Expected Result:
Rate limiting applied.

---

### TC-BUS-03 Account Enumeration
API: Forget Password

Steps:
Test with existing and non-existing emails.

Expected Result:
Both responses identical.

---

# 5. Rate Limiting Tests

### TC-RATE-01 Password Reset Abuse
API: Forget Password

Steps:
Send 50 requests in short time.

Expected Result:
API returns 429 Too Many Requests.

---

# 6. Sensitive Data Exposure

### TC-DATA-01 Sensitive Data in Response

Verify that responses do not contain:

- passwords
- tokens
- internal database IDs
- stack traces

Expected Result:
No sensitive information exposed.

---

# 7. Error Handling

### TC-ERR-01 Internal Error Leakage

Steps:
Send malformed payload.

Expected Result:
API returns generic error message without stack trace.

---

# 8. Logging & Monitoring

Verify that suspicious activities such as repeated OTP attempts are logged for security monitoring.

---

# Conclusion

These tests cover the most critical attack vectors including:

- Authentication vulnerabilities
- Authorization bypass
- Injection attacks
- Input validation
- Business logic abuse
- Rate limiting
- Sensitive data exposure

The automated tests implemented using RestAssured validate these scenarios during CI/CD execution.