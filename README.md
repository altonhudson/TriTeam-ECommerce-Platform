# TriTeam-ECommerce-Platform

# CPAN-228 Web Application Development: Semester Project

### Team Roster & Introductions
* **Group Name:** Tri-Team
* **Developer 1:** Alton Hudson - (*developer intro*)
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
![ER Diagram](images/E-Commerce_Entity_Relationship_Diagram_.webp)

### UI Layout Design & Wireframes
Below are the wireframes illustrating our main application pages:

* **Home / Dashboard View:** Features a top banner, a floating navigation bar with a search component, and an evenly tiled category layout utilizing lazy loading.
  * ![Home Screen View](images/HomeScreen.jpg)
* **List View:** Displays active product categories within a tile container paired with a functional filter component
  * ![Category List View](images/Category.jpg)
* **Cart Summary Flow:** Outlines product tiles alongside a transaction subtotal breakdown.
  * ![Cart Summary View](images/CartSummary.jpg)
* **Login View:** an authentication screen containing a centralized login form within the content container.
  * ![Login Screen View](images/Login.jpg)

### How to Run the Project
1. Clone this repository using Git.
2. Open the project folder in your Java IDE of choice
3. Ensure Maven dependencies are downloaded (Web, Thymeleaf, JPA, Security and Data REST).
4. Run the main Spring Boot application class.
5. Open a web browser and navigate to `http://localhost:8080`.
