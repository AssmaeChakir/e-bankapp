
# Bank Management Application

## Overview

This is a full-stack bank management application that allows clients to manage their bank accounts, view account details, perform bank transfers, and track their banking operations.

* **Backend**: Spring Boot REST API with JWT authentication and role-based authorization.
* **Frontend**: React application for client dashboard and bank operations.
* **Database**: JPA / Hibernate with a relational database (e.g., MySQL, MariaDB).

---

## Features

* User authentication and authorization (role-based access)
* View client bank accounts and account details
* Display paginated bank operations per account
* Perform new bank transfers with validation:

  * Account status check (not blocked or closed)
  * Balance sufficiency verification
  * Transaction recording (debit and credit)
* Secure endpoints with JWT tokens

---

## Prerequisites

* Java 17+
* Maven or Gradle
* Node.js 18+
* A relational database (MySQL, MariaDB, PostgreSQL, etc.)
* Postman or similar tool for API testing

---

## Setup and Running

### Backend

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/bank-management.git
   cd bank-management/backend
   ```

2. Configure your database settings in `application.properties` or `application.yml`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bank_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build and run the Spring Boot backend:

   ```bash
   ./mvnw spring-boot:run
   ```

   The backend will start at `http://localhost:8082`

---

### Frontend

1. Navigate to the frontend directory:

   ```bash
   cd ../frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Configure API base URL if necessary (e.g., in `.env` or config files):

```
   VITE_API_URL=http://localhost:8082/api
   ```

4. Start the React development server:

   ```bash
   npm run dev
   ```

   The frontend will be available at `http://localhost:5173` 

---

## API Endpoints

### Authentication

* `POST /api/auth/login` – Authenticate user and receive JWT token

### Bank Accounts

* `GET /api/accounts/my-accounts` – List all accounts for the logged-in client
* `GET /api/accounts/me` – Get details of a single account (default or specified)
* `GET /api/accounts/operations?rib={rib}&page={page}&size={size}` – Paginated operations for account
* `POST /api/accounts/transfer` – Perform a bank transfer (secured)

---

## Testing with Postman

1. Authenticate and obtain JWT token.
2. For secured endpoints, add header:

   ```
   Authorization: Bearer <your_jwt_token>
   ```
3. Use the provided endpoints to fetch data or perform operations.

---

## Important Notes

* Ensure CORS is configured in the backend to allow frontend requests.
* Validate all inputs on both frontend and backend.
* Sensitive information (JWT secret, DB passwords) should be stored securely (e.g., environment variables).

---

## License

This project is licensed under the MIT License.

---

## Contact

For questions or support, please contact \[[your.email@example.com](mailto:your.email@example.com)]