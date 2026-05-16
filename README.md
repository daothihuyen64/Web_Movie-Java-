# 🎬 Web Xem Phim – Movie Streaming Web Application

A full-stack movie streaming web application with user authentication, subscription packages, movie management, and interactive features.

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.3.4 |
| Security | Spring Security + JWT (JJWT 0.11.2) |
| ORM | Spring Data JPA + Hibernate |
| Database | MySQL |
| Mapping | ModelMapper 2.4.5 |
| Build Tool | Maven |
| Frontend | Vue.js 3, Vue Router 4, Vuex 4, Axios |

## 📁 Project Structure

```
Web_Movie-Java/
├── Back_End/demo/
│   └── src/main/java/com/webxemphim/demo/
│       ├── config/          # CORS, ModelMapper config
│       ├── controller/      # REST API endpoints
│       ├── dto/             # Data Transfer Objects
│       ├── entity/          # JPA Entities
│       ├── enums/           # EmojiType enum
│       ├── exception/       # Custom exceptions
│       ├── payload/         # Request/Response wrappers
│       ├── repository/      # Spring Data JPA Repositories
│       ├── security/        # JWT Filter, Security Config
│       ├── service/         # Business Logic
│       └── utils/           # JWT Utilities
└── Front_End/
    └── src/
        ├── axios.js         # Axios instance config
        ├── router/          # Vue Router
        └── store/           # Vuex store (auth module)
```

## 🗄 Database Entities

- **Movie** – name, poster, trailer, description, director, totalEpisodes, views, ratingMean, status
- **Episode** – linked to Movie
- **Actor** / **Movie_Actor** – many-to-many between movies and actors
- **Genre**, **Country**, **Release_Year** – movie metadata
- **User** / **Role** – user account & role-based access
- **Packages** / **Transaction** – subscription plans & purchase history
- **Rating** – star rating per user per movie
- **React** – emoji reactions (like, love, etc.) on movies
- **Comment** – user comments on movies
- **Favorite_Movie** – user's watchlist/favorites

## 🔐 Authentication & Security

- **Stateless JWT-based authentication** (no session)
- `CustomFilterSecurity`: configures Spring Security filter chain
- `CustomerJwtFilter`: validates JWT on every request
- `CustomUserDetailService`: loads user by username
- `JwtUtilsHelper`: token generation and validation
- Passwords hashed with **BCrypt**
- Public endpoint: `POST /login/**` (sign in, sign up)
- All other endpoints require a valid JWT token

## 🚀 API Endpoints Overview

| Resource | Base Path |
|---|---|
| Authentication | `/login` |
| Movies | `/movie` |
| Episodes | `/episode` |
| Actors | `/actor` |
| Genres | `/genre` |
| Countries | `/country` |
| Release Years | `/release-year` |
| Users | `/user` |
| Subscription Packages | `/packages` |
| Ratings | `/rating` |
| Reactions | `/react` |
| Comments | `/comment` |

## ⚙️ Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+
- Node.js 16+ / npm

### Backend Setup

```bash
cd Back_End/demo

# Configure database in src/main/resources/application.properties
# spring.datasource.url=jdbc:mysql://localhost:3306/webxemphim
# spring.datasource.username=root
# spring.datasource.password=yourpassword

./mvnw spring-boot:run
```

Backend runs at: `http://localhost:8080`

### Frontend Setup

```bash
cd Front_End
npm install
npm run serve
```

Frontend runs at: `http://localhost:8081`

## 🔄 Key Features

- ✅ JWT authentication (login / register / logout)
- ✅ Full CRUD for movies, episodes, actors
- ✅ Filter movies by genre, country, release year
- ✅ User subscription packages with transaction history
- ✅ Star rating system
- ✅ Emoji reactions on movies
- ✅ Comment system
- ✅ Favorite/watchlist management
- ✅ Role-based access control (Admin / User)
- ✅ Soft delete (status field) for movies
