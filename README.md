# Zest India - Product Management Backend API

A robust, enterprise-grade RESTful API built with Java and Spring Boot for managing products and their associated items, featuring stateless JWT security, role-based access control, token rotation, and comprehensive documentation.

## 🚀 Technical Stack
* **Language:** Java 17+
* **Framework:** Spring Boot
* **Security:** Spring Security 6, JWT (JSON Web Tokens) with Refresh Token Rotation
* **Persistence:** Spring Data JPA (Hibernate), MySQL, Database Indexing
* **Validation & Errors:** Jakarta Validation, Global Exception Handling (`@RestControllerAdvice`)
* **API Documentation:** SpringDoc OpenAPI / Swagger UI
* **Testing:** JUnit 5, Mockito
* **Deployment:** Docker & Docker Compose

---

## 📌 API Endpoints & Structure

### Authentication (`/api/v1/auth`)
* `POST /api/v1/auth/register` - Register a new user and receive access/refresh tokens
* `POST /api/v1/auth/authenticate` - Login with credentials to receive tokens
* `POST /api/v1/auth/refresh` - Rotate refresh tokens to generate a new access token

### Products (`/api/v1/products`)
* `GET /api/v1/products` - Retrieve a paginated list of all products (supports `page` and `size` parameters)
* `GET /api/v1/products/{id}` - Retrieve a single product by ID
* `POST /api/v1/products` - Create a new product (secured, requires `Authorization: Bearer <token>`)
* `PUT /api/v1/products/{id}` - Update an existing product
* `DELETE /api/v1/products/{id}` - Delete a product
* `GET /api/v1/products/{id}/items` - Fetch all items linked to a specific product

---

## 🛠️ Local Setup & Installation

1. **Clone the Repository:**
   ```bash
   git clone [https://github.com/Gaurav-Mali/product-management-api.git](https://github.com/Gaurav-Mali/product-management-api.git)
   cd product-management-api