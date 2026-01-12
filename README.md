# Student-Course Management System

A Spring Boot-based web application to manage students, courses, and their many-to-many enrollment relationships with complete tracking of enrollment history.

## 🚀 Features

- **Student Management**: Create, view, update, and delete student records.
- **Course Management**: Create, view, update, and delete course records.
- **Enrollment Tracking**: 
  - Enroll students in multiple courses.
  - Track enrollment and unenrollment dates.
  - Maintain a status for each enrollment (ACTIVE/DROPPED).
  - View enrollment history from both Student and Course perspectives.
- **Responsive UI**: Built with Thymeleaf and Bootstrap for a clean, user-friendly experience.

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot 3.4.1**
- **Spring Data JPA**
- **Thymeleaf** (Template Engine)
- **PostgreSQL** (Production Database)
- **H2 Database** (Testing)
- **Lombok**
- **Bootstrap 5 & FontAwesome**

## 📋 Prerequisites

- JDK 17 or higher
- Maven 3.6+
- PostgreSQL installed and running

## ⚙️ Configuration

1. **Database Setup**: Create a database named `springboot` in your PostgreSQL instance.
2. **Update Properties**: Open `src/main/resources/application.properties` and update the database credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/springboot
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

## 🏃 Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/chintanpatel/SpringBootManyToManyApp.git
   cd SpringBootManyToManyApp
   ```

2. **Build the project**:
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the UI**:
   Open your browser and navigate to `http://localhost:8080`

## 🧪 Running Tests

The project includes unit and integration tests using JUnit 5 and Mockito. To run the tests:

```bash
./mvnw test
```

## 📁 Project Structure

- `org.chintanpatel.springbootmanytomanyapp.student`: Student entity, repository, and controller.
- `org.chintanpatel.springbootmanytomanyapp.course`: Course entity, repository, and controller.
- `org.chintanpatel.springbootmanytomanyapp.enrollment`: Enrollment entity (Join Table with extra columns), repository, and service.
- `src/main/resources/templates`: HTML templates using Thymeleaf.
- `src/main/resources/static`: CSS and JS assets (Bootstrap, FontAwesome).

## 📄 License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details (if available).
