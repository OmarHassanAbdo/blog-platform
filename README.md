# 📰 Blog Platform

A full-featured **Spring Boot Blog Application** that supports **user management, posts, comments, likes, JWT authentication, role-based authorization, and password reset via email.**  
Built with modern Spring technologies following best practices for **clean architecture, security, and maintainability.**

---

## 🚀 Features

### 👤 User Management
- Register new users with validation  
- Login with **JWT authentication**  
- Role-based authorization (`ROLE_USER`, `ROLE_ADMIN`)  
- Profile photo and bio support  
- Follow and friend system (self-referencing many-to-many)  
- Secure password storage using **BCrypt**  
- **Password reset via email** (token-based)

### 🧾 Post Management
- Create, update, and delete posts  
- Attach optional image  
- View all posts or by specific user  
- View posts with comments and likes  

### 💬 Comments
- Comment on posts  
- Edit or delete own comments  
- View all comments on a post  

### ❤️ Likes
- Like/unlike posts or comments  
- **Database constraint:** user can only like a given post/comment once  
- `@Check` constraint ensures that exactly one of `post_id` or `comment_id` is non-null  

### 🔐 Security
- Spring Security with **JWT integration**  
- Role-based method and endpoint access control  
- Secure password hashing  
- Stateless authentication (no sessions)  
- Email-based password reset with expirable verification tokens  

### 🧱 Architecture
- **Spring Boot 3 / Java 17**
- **Spring Data JPA** (Hibernate ORM)
- **Spring Security + JWT**
- **Spring Validation**
- **Spring Mail**
- **Lombok**
- **PostgreSQL/MySQL (configurable)**
- **Maven** for build management

---

## 🧩 Entity Structure

### 🧍 User
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary Key |
| username | String | Unique username |
| email | String | Unique email |
| password | String | Encrypted password |
| profilePhoto | String | Profile image URL |
| bio | String | Short bio |
| roles | Set<Role> | Many-to-many with `Role` |
| friends | Set<User> | Self-referencing many-to-many |
| posts | List<Post> | One-to-many |
| comments | List<Comment> | One-to-many |
| likes | List<Like> | One-to-many |

---

### 🧭 Role
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary Key |
| roleName | Enum(AppRole) | `ROLE_USER`, `ROLE_ADMIN` |
| users | List<User> | One-to-many |

---

### 📝 Post
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary Key |
| text | String | Post content |
| image | String | Optional image URL |
| author | User | Many-to-one |
| likes | List<Like> | One-to-many |
| comments | List<Comment> | One-to-many |
| createdAt | LocalDateTime | Auto-generated |
| updatedAt | LocalDateTime | Auto-updated |

---

### 💭 Comment
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary Key |
| text | String | Comment content |
| author | User | Many-to-one |
| post | Post | Many-to-one |
| likes | List<Like> | One-to-many |
| createdAt | LocalDateTime | Auto-generated |

---

### ❤️ Like
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary Key |
| user | User | Many-to-one |
| post | Post | Nullable |
| comment | Comment | Nullable |
| createdAt | LocalDateTime | Auto-generated |
| Constraints | `CHECK` ensures one of `post_id` or `comment_id` is not null |

---

## 🧠 Authentication Flow

1. User registers → password encrypted with BCrypt.  
2. User logs in → receives **JWT access token**.  
3. Client includes token in `Authorization: Bearer <token>` header.  
4. Spring Security filters validate JWT and set authentication context.  
5. Authorization rules protect endpoints by role.  
6. For password reset:
   - User requests reset → email sent with tokenized link.  
   - User clicks link → enters new password → verified and saved securely.

---

## ⚙️ Technologies Used

| Category | Stack |
|-----------|--------|
| **Backend** | Spring Boot 3, Java 17 |
| **Security** | Spring Security, JWT |
| **Database** | PostgreSQL / MySQL |
| **ORM** | Spring Data JPA (Hibernate) |
| **Mailing** | Spring Mail + SMTP |
| **Build Tool** | Maven |
| **Validation** | Spring Boot Starter Validation |
| **Lombok** | For reducing boilerplate |
| **Testing (Optional)** | JUnit 5, Mockito |

---

## 🧰 Project Setup

### 🪜 Prerequisites
- Java 17+
- Maven 3+
- PostgreSQL or MySQL
- Valid SMTP credentials (for password reset emails)

### ⚡ Installation Steps

```bash
# 1. Clone repository
git clone https://github.com/OmarHassanAbdo/blog-platform.git
cd blog-platform

# 2. Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:5432/blog_db
spring.datasource.username=mysql
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.mail.host=smtp.gmail.com
spring.mail.username=omarhassan62548@gmail.com
spring.mail.password=your-app-password
jwt.secret=your-secret-key

# 3. Build and run
mvn spring-boot:run
```

### 🔑 Testing JWT
After login, you’ll receive a token like:
```
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
}
```
Use it in all subsequent requests:
```
Authorization: Bearer <token>
```

---

## 🧪 Example User Roles

| Username | Role | Permissions |
|-----------|------|-------------|
| user1 | ROLE_USER | Create posts, comment, like |
| admin | ROLE_ADMIN | Manage users and posts |

---

## 🧱 Project Structure

```
src/
 └── main/
     ├── java/com/example/blog_platform/
     │   ├── config/           # Security, JWT, Mail configs
     │   ├── controller/       # REST controllers
     │   ├── dto/              # Data transfer objects
     │   ├── entity/           # JPA entities
     │   ├── enums/            # Enum definitions
     │   ├── repository/       # JPA repositories
     │   ├── service/          # Service layer
     │   ├── security/         # UserDetails + JWT
     │   └── BlogPlatformApp.java
     └── resources/
         ├── application.properties
         └── templates/emails/
```

---

## 📬 Contact
**Author:** Omar Hassan  
📧 [omarhassan62548@gmail.com](mailto:omarhassan62548@gmail.com)  
🌐 [GitHub Profile](https://github.com/OmarHassanAbdo)
