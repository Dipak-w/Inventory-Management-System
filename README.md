# Inventory-Management-System
Java Spring Boot project named **Inventory Management System** 
# 📦 Inventory Management System

A secure, RESTful Inventory Management backend built with **Spring Boot**, **JWT authentication**, **role-based access**, and **OWASP Top 10** best practices. Designed as a capstone project to demonstrate scalable architecture, secure coding, and collaborative development.

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.3.2-brightgreen.svg)
![Build](https://github.com/Dipak-w/inventory-management-system/actions/workflows/ci.yml/badge.svg)
![License](https://img.shields.io/github/license/Dipak-w/inventory-management-system)

---

## 🚀 Features

- ✅ JWT authentication with role-based access (ADMIN, USER)
- ✅ CRUD operations for Products, Categories, Suppliers
- ✅ JPA persistence with PostgreSQL/MySQL
- ✅ Input validation and centralized exception handling
- ✅ OWASP Top 10 security practices
- ✅ Swagger/OpenAPI documentation
- ✅ JUnit 5 tests with coverage for critical services
- ✅ Dockerfile and docker-compose setup
- ✅ GitHub Actions CI pipeline

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.3.2
- Spring Security
- Spring Data JPA
- JWT (jjwt)
- PostgreSQL / MySQL
- JUnit 5 + Mockito
- Swagger (springdoc-openapi)
- Docker + GitHub Actions

---

## 📦 Setup Instructions

```bash
# Clone the repository
git clone https://github.com/Dipak-w/inventory-management-system.git
cd inventory-management-system

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
