


# TriTeam-ECommerce-Platform

# CPAN-228 Web Application Development: Semester Project

### Team Roster & Introductions
* **Group Name:** Tri-Team
* **Developer 1:** Alton Hudson - I am a member of our e-commerce project using Spring Boot. I’m building my skills in software development and enjoy gaining hands-on experience by working on real projects.
* **Developer 2:** Anthony Murphy - (*developer intro*)
* **Developer 3:** Matthew Walker - (*developer intro*)

### Category Choice & Rationale
* **Selected Category:** Category 1: E-Commerce Platform
* **Why We Chose This Category:** We chose the E-Commerce Platform because it is a traditional business domain with clear CRUD operations and a straightforward structure for applying Spring concepts. It also reflects a real-world system with enough complexity to be challenging without being overwhelming.

### Domain Model & Entity Definitions
Our domain model maps out an inventory and distribution ecosystem using the following 7 entities:

* **SUPPLIER:** `SupplierID` (PK), `CompanyName`, `ContactName`, `ContactEmail`, `Phone`, `LeadTimeDays`, `MinOrderQty`
* **PRODUCT:** `ProductID` (PK), `SKU`, `Name`, `Category`, `UnitPrice`, `WeightKg`, `Description`
* **INVENTORY:** `InventoryID` (PK), `ProductID` (FK), `LocationID` (FK), `QtyOnHand`, `LastUpdated`, `ReorderPoint`, `ReorderQty`
* **LOCATION:** `LocationID` (PK), `Name`, `Address`, `Type`, `CapacityUnits`
* **ORDER:** `OrderID` (PK), `OrderNumber`, `Status`, `OrderDate`, `TaxAmount`, `ShippingCost`, `ShippingAddress`, `TrackingNumber`, `TotalAmount`, `Notes`
* **ORDER_ITEM:** `OrderItemID` (PK), `OrderNumber` (FK), `ProductID` (FK), `Quantity`, `UnitPrice`, `Subtotal`
* **TRANSFER:** `TransferID` (PK), `FromLocationID` (FK), `ToLocationID` (FK), `ProductID` (FK), `Quantity`, `Status`, `TransferDate`, `Notes`

#### Domain Model ERD
<img width="1020" height="868" alt="Entity Relationship Diagram1" src="https://github.com/user-attachments/assets/b6a76a83-80a6-4294-8fc1-2269d4ad9721" />

### UI Layout Design & Wireframes
Below are the wireframes illustrating our main application pages:

* **Home / Dashboard View:** Features a top banner, a floating navigation bar with a search component, and an evenly tiled category layout utilizing lazy loading.
  <img width="1920" height="1080" alt="HomeScreen" src="https://github.com/user-attachments/assets/c3ce6621-dad3-44b8-a2b6-8b49e07f0cf1" />
* **List View:** Displays active product categories within a tile container paired with a functional filter component
  <img width="1920" height="1080" alt="Category" src="https://github.com/user-attachments/assets/3c2c5746-a595-46e1-b135-d5496c1d3627" />
* **Cart Summary Flow:** Outlines product tiles alongside a transaction subtotal breakdown.
  <img width="1920" height="1080" alt="CartSummary" src="https://github.com/user-attachments/assets/d0a6fa38-2a0f-47d6-b30b-a813b04d6f66" />
* **Login View:** an authentication screen containing a centralized login form within the content container.
  <img width="1920" height="1080" alt="Login" src="https://github.com/user-attachments/assets/45b6375b-d341-45ec-94cd-ff2b03ee44a9" />

### How to Run the Project
1. Clone this repository using Git.
2. Open the project folder in your Java IDE of choice
3. Ensure Maven dependencies are downloaded (Web, Thymeleaf, JPA, Security and Data REST).
4. Run the main Spring Boot application class.
5. Open a web browser and navigate to `http://localhost:8080`.
