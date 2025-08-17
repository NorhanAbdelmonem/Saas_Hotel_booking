🏨 Hotel Booking Platform

📌 Overview
This project is a multi-vendor hotel booking platform(SaaS) built with Spring Boot.
It allows:

Public users to browse hotels, rooms, and reviews.

Customers to register, log in, book rooms, and write reviews.

Managers to manage their hotels, rooms, and reservations.

Admins to oversee the whole system.

The platform is secure, modular, and extendable, with JWT-based authentication and role-based authorization.

🚀 Features
🔓 Public
View list of hotels with sorting & filtering
View rooms of each hotel
Read hotel reviews


👤 Customer
Register & authenticate with JWT
Browse hotels and rooms
Book a room & manage own bookings
Write & edit reviews
View personal profile

🏢 Manager
Manage owned hotels
Manage rooms in their hotels
View bookings related to their hotels
See reviews of their hotels

🛠 Admin

Full access to system resources
Manage users and vendors

🔐 Security & Roles
Implemented with Spring Security + JWT.
PUBLIC: can access hotels, rooms, and reviews browsing endpoints.
CUSTOMER: booking, personal reviews, profile.
MANAGER: CRUD operations on their hotels/rooms + related bookings & reviews.
ADMIN: system-level management.
Security rules are modular (SecurityRules interface) and injected into SecurityConfig.

🏗 Tech Stack

Backend: Spring Boot 3, Spring Security, JWT
Database: MySQL  + JPA/Hibernate
Validation: Jakarta Validation
Build Tool: Maven/Gradle
Other: Lombok, DTO Mapping
