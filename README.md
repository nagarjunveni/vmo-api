# VMO API - Authorized Signature Management

This Spring Boot application provides REST APIs for managing Authorized Signatures.

## Prerequisites

- Java 21
- Maven
- MySQL Database

## Database Configuration

Configure your MySQL database connection in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vmo_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

## Running the Application

```bash
mvn spring-boot:run
```

The application will start on port 8080.

## API Endpoints

### Create a new Authorized Signature

- **URL**: `/api/authorized-signatures`
- **Method**: `POST`
- **Content-Type**: `multipart/form-data`
- **Request Body**:
  - `firstName` (required): First name of the authorized person
  - `middleName` (optional): Middle name of the authorized person
  - `lastName` (required): Last name of the authorized person
  - `email` (required): Email address (must be unique)
  - `contactNumber` (required): Contact number
  - `digitalSignature` (optional): Digital signature file

### Update an Authorized Signature

- **URL**: `/api/authorized-signatures/{id}`
- **Method**: `PUT`
- **Content-Type**: `multipart/form-data`
- **Path Variable**: `id` - ID of the authorized signature to update
- **Request Body**: Same as create endpoint

### Get an Authorized Signature by ID

- **URL**: `/api/authorized-signatures/{id}`
- **Method**: `GET`
- **Path Variable**: `id` - ID of the authorized signature to retrieve

### Get All Active Authorized Signatures

- **URL**: `/api/authorized-signatures`
- **Method**: `GET`
- **Description**: Returns all authorized signatures where status is true

### Delete an Authorized Signature (Soft Delete)

- **URL**: `/api/authorized-signatures/{id}`
- **Method**: `DELETE`
- **Path Variable**: `id` - ID of the authorized signature to delete
- **Description**: Sets the status to false instead of performing a hard delete

### Download Digital Signature

- **URL**: `/api/authorized-signatures/{id}/digital-signature`
- **Method**: `GET`
- **Path Variable**: `id` - ID of the authorized signature
- **Description**: Downloads the digital signature file

## Response Format

```json
{
  "id": 1,
  "firstName": "John",
  "middleName": "Robert",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "contactNumber": "1234567890",
  "hasDigitalSignature": true,
  "status": true,
  "createdAt": "2023-06-15T10:30:00",
  "updatedAt": "2023-06-15T10:30:00"
}
```