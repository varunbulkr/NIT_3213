# 📱 NIT3213 Android Application – Final Assignment

## 🎯 Objective

Develop an Android application demonstrating key Android development skills including:
- API integration
- User interface design
- Clean architecture
- Dependency Injection
- Unit testing

This app is built for the **NIT3213 Mobile Application Development** unit.

---

## 📘 Project Overview

This application features three main screens:

1. **Login Screen** – Authenticates users.
2. **Dashboard Screen** – Displays a list of entities retrieved from the API.
3. **Details Screen** – Shows full details of a selected entity.

---

## 🌐 API Details

**Base URL:** `https://nit3213api.onrender.com/`

### 🔐 Authentication Endpoint

- **Path:** `/footscray/auth`, `/sydney/auth`, or `/ort/auth`
- **Method:** POST  
- **Body:**
```json
{
  "username": "YourFirstName",
  "password": "sYourStudentID"
}
