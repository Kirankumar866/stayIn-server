

# Hotel Reservation System

This project is a comprehensive **Hotel Reservation System** developed using **Spring Boot** for the backend and **ReactJS** for the frontend. The system features robust role-based access control (RBAC), user management, room reservations, and booking management to enhance the booking process and streamline hotel operations.

## Features

### Role-Based Authentication
- **Secure Role-Based Access Control (RBAC)**: Different user roles (Admin, Employee) with distinct permissions.
  - **Admin**: Manages room inventory, user management, and system settings.
  - **Employee**: Responsible for room booking, check-ins, and check-outs.
  
- **JWT Authentication**: Secures the system by using **Spring Security** with **JWT** tokens to manage and verify user sessions.

### User and Room Management
- **User Management**: Manage user accounts, including registration, login, and role assignments.
- **Room Reservations**: Enables users to view available rooms, reserve rooms, and manage bookings.
  
### Frontend Development
- **Responsive UI**: Built with **ReactJS**, **HTML5**, **CSS3**, and **JavaScript**, providing a smooth and intuitive user experience.
- **Modular and Reusable Components**: Utilized ReactJS components for a maintainable and consistent UI.

### Backend Development
- **Microservices Architecture**: Implemented using **Spring Boot**, **Spring MVC**, **Spring AOP**, and **Spring Batch** for modular, scalable services.
- **Data Processing**: Efficiently handled large datasets using **Spring Batch** for room and user data processing tasks.
  
### Database Design
- **MySQL**: Used **MySQL** for database management, ensuring optimized schemas, queries, and stored procedures for better performance.
- **Hibernate ORM**: Integrated **Hibernate ORM** for object-relational mapping and seamless database interaction.

### Key Functionalities
- **Room Booking**: Users can browse available rooms, check details, and reserve rooms based on dates and availability.
- **Booking Management**: Admins and employees can view, update, and cancel bookings as necessary.
- **Inventory Control**: Admins can manage room availability, rates, and user details.

## Technologies Used

- **Backend**: 
  - Spring Boot
  - Spring MVC, AOP, Batch
  - JWT Token-based Authentication
  - Hibernate ORM
  - MySQL

- **Frontend**: 
  - ReactJS
  - HTML5, CSS3, JavaScript

- **Other Tools**:
  - Node.js (for front-end dependencies)
  - Maven/Gradle (for managing Java dependencies)

## Installation

### Prerequisites
- **Node.js** and **npm** for frontend.
- **Java 8** or higher.
- **MySQL** database installed.

### Backend Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/hotel-reservation-system.git
   cd hotel-reservation-system
   ```

2. Navigate to the backend directory and build the project using Maven/Gradle:
   ```bash
   mvn clean install
   ```

3. Configure the database by updating the **application.properties** file with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

4. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install the necessary dependencies:
   ```bash
   npm install
   ```

3. Run the React development server:
   ```bash
   npm start
   ```

4. Open the app in your browser at `http://localhost:3000`.

## Usage

- **Admins**: Log in with admin credentials to manage room inventory and user accounts.
- **Employees**: Log in with employee credentials to handle room bookings and check-ins/check-outs.
- **Guests**: Register an account and make room reservations, view bookings, and manage their profile.

## Future Enhancements

- **Payment Integration**: Add payment gateway support for processing bookings.
- **Advanced Room Search**: Include filters such as room type, amenities, and pricing.
- **Automated Notifications**: Send email or SMS notifications for booking confirmations and reminders.

## Contributing

Feel free to fork the repository and submit pull requests with improvements or bug fixes.

## License

This project is licensed under the MIT License.

