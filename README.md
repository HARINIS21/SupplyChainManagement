# Supply Chain Management System (SCMS)

## Project Overview
The Supply Chain Management System (SCMS) is a backend Java application designed to manage the flow of goods from suppliers to customers. It handles:

- Inventory management
- Purchase orders
- Sales orders
- Shipments
- Warehouse stock tracking

This project uses *Java, **Maven, and **TestNG* for testing.

---

## Project Structure

supply-chain-management/
├── src/
│   ├── main/
│   │   ├── java/com/company/scm/
│   │   │   ├── model/          # Classes for entities like Product, Supplier, Order
│   │   │   ├── service/        # Business logic like InventoryService, OrderService
│   │   │   └── repository/     # In-memory data storage
│   └── test/
│       └── java/com/company/scm/  # TestNG test cases
├── pom.xml                     # Maven configuration
└── README.md                   # Project documentation


---

## Prerequisites
- *Java JDK 17 or higher*  
  [Download Java](https://www.oracle.com/java/technologies/javase-downloads.html)
- *Maven*  
  [Download Maven](https://maven.apache.org/download.cgi)
- *Git* (for version control and GitHub)  
  [Download Git](https://git-scm.com/downloads)

---

## Setup Instructions

1. *Clone the repository*
   git clone <your-repo-url>
   cd Supply-Chain-Management

2. Build the project using Maven
   mvn clean install

3. Run the application
The project is a CLI-based Java application. Run the main class:
mvn exec:java -Dexec.mainClass="com.company.scm.Main"

Follow the prompts to manage inventory, orders, and shipments.


# Running Tests

The project uses TestNG for testing. To run all test cases:
mvn test

All test cases are in src/test/java/com/company/scm.


# Key Features
•	Add, update, and view products in inventory
•	Create purchase and sales orders
•	Track shipments and stock levels in warehouses
•	Handle supplier and customer data
•	Comprehensive test suite for all functionalities


# Challenges Faced
•	Handling in-memory data management for multiple entities
•	Maintaining proper relationships between orders, inventory, and warehouses
•	Writing complete TestNG test cases for all services


Author
Harini S
