# Sports Event Calendar - Backend Engineering Solution

# Overview
This project is a robust, Spring Boot-powered sports calendar application. It was developed to demonstrate best practices in database normalization, RESTful API design, and dynamic frontend integration.

# Technical Decisions & Architecture
* **3NF Database Normalization**: While the source data provided was in a flat JSON format, I restructured the schema into Third Normal Form (3NF). I separated 'Sports', 'Teams', and 'Events' into relational tables to eliminate data redundancy and ensure referential integrity.
* **Naming Conventions**: Strictly adhered to the requirement of prefixing foreign keys with an underscore (e.g., `_sport_id`, `_home_team_id`) to maintain consistency with the provided technical specifications.
* **In-Memory Portability**: Utilized an H2 database to allow the application to be tested immediately without the need for external database configuration.
* **Data Integrity**: Implemented a custom `DataLoader` component. This service handles the initial ingestion of the `data.json` file, ensuring that entities like Teams and Sports are not duplicated during the seeding process.

# How to Run
1. Ensure you have **Java 21** installed.
2. Open the project in your IDE (IntelliJ/Eclipse).
3. Run the `EventCalendarApplication` class.
4. Access the dashboard at: `http://localhost:8080/index.html`

# API Endpoints
* `GET /api/events` - Retrieves all scheduled and past events.
* `GET /api/events/{id}` - Retrieves a specific event by its ID.
* `POST /api/events` - Allows for the manual addition of new sports events.
