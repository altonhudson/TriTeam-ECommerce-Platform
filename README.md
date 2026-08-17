# TriTeam-ECommerce-Platform

# CPAN-228 Web Application Development: Semester Project

### Team Roster & Introductions

- **Group Name:** Tri-Team
- **Developer 1:** Alton Hudson - I am a member of our e-commerce project using Spring Boot. I’m building my skills in software development and enjoy gaining hands-on experience by working on real projects.
- **Developer 2:** Anthony Murphy - As a Tri-Team developer I plan on building my skills and knowledge of Java web development to create functional and scalable software with real world applications.
- **Developer 3:** Matthew Walker - I enjoy turning ideas into practical applications, which is why I’m excited to work on our e-commerce project. My goal is to strengthen my web development skills and learn more about Java frameworks.

### Deliverable 1: Team Contributions

- **Alton Hudson:** Created the JPA entity classes and repository interfaces for the project's database tables. Wrote the dummy data used to populate the database so the application starts with realistic sample records that the team can use for testing and development.
- **Anthony Murphy:** Created Controllers and Services that handle endpoints and data submission, performed bug fixing and project alignment ensuring cohesion of all branches.
- **Matthew Walker:** Implemented the Thymeleaf frontend templates (home, category, add-product), built server-side pagination & search logic in `CategoryController` and configured server-side form validation for the `Product` entity.

### Configuration & Profiles
 
The application has been configured with a hierarchical structure of YAML files:
- `application.yml` - settings shared in every environment (consists of: application name, server port, SQL logging).
- `application-dev.yml` - dev profile. Uses an in-memory **H2** database with the H2 console enabled.
- `application-prod.yml` - production/QA profile. Connects to a persistent MySQL db.
Switching between these environments can be performed by setting a different profile at startup (explained in the 'How to Run the Project' section below). If no profile is specified, the application defaults to `dev`.
 
| | `dev` | `prod` |
|---|---|---|
| Database | H2, in-memory | MySQL, persistent |
| Schema | Rebuilt on every start | Preserved between runs |
| Sample data | Seeded from `data.sql` | Not seeded |
| H2 console | Enabled at `/h2-console` | Disabled |
| Setup required | None | Local MySQL server |
 
### How to Run the Project
 
1. Clone this repository.
2. Open your terminal and navigate into the `ecommerce` directory (where the `pom.xml` file is located).
3. Run the application using the included Maven wrapper.
   **Development profile (H2, no setup required):**
   - **Mac/Linux:** `./mvnw clean spring-boot:run`
   - **Windows:** `.\mvnw.cmd clean spring-boot:run`
   **Production/QA profile (requires MySQL):**
   - **Mac/Linux:** `./mvnw spring-boot:run -Dspring-boot.run.profiles=prod`
   - **Windows:** `.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=prod"` 
4. Once the server starts up, open a web browser and navigate to `http://localhost:8080`.
5. Log in using the following admin credentials:
   - **Username:** `admin`
   - **Password:** `admin123`
#### Running with the `prod` profile
 
The `prod` profile requires a local MySQL server running on port 3306. The database does not need to be created manually. The connection URL includes `createDatabaseIfNotExist=true` so MySQL creates the `triteam_ecommerce` schema on first run and Hibernate builds the tables.
 
Credentials are injected via environment variables each having a fallback value so the app runs without any additional configuration:
 
| Variable | Default | Description |
|---|---|---|
| `DB_HOST` | `localhost` | MySQL host |
| `DB_PORT` | `3306` | MySQL port |
| `DB_NAME` | `triteam_ecommerce` | Schema name |
| `DB_USERNAME` | `root` | MySQL user |
| `DB_PASSWORD` | `root` | MySQL password |
 
**Important note:** If your MySQL root password is not `root`, set `DB_PASSWORD` before starting the application:
 
- **Mac/Linux:** `DB_PASSWORD=yourpassword ./mvnw spring-boot:run -Dspring-boot.run.profiles=prod`
- **Windows (PowerShell):** `$env:DB_PASSWORD="yourpassword"` then run the `prod` command above.
Note that the `prod` profile starts with an empty product catalogue. Sample data from `data.sql` is only loaded into the in-memory `dev` database, which prevents duplicate records from accumulating in a persistent database on every restart. Products can be added through the admin interface and will persist between runs.
 
#### Accessing the H2 console (`dev` only)
 
With the application running under the `dev` profile, log in as an administrator and navigate to `http://localhost:8080/h2-console`. Connect using:
- **JDBC URL:** `jdbc:h2:mem:triteamdb`
- **User Name:** `sa`
- **Password:** *(leave blank)*

### Category Choice & Rationale

- **Selected Category:** Category 1: E-Commerce Platform
- **Why We Chose This Category:** We chose the E-Commerce Platform because it is a traditional business domain with clear CRUD operations and a straightforward structure for applying Spring concepts. It also reflects a real-world system with enough complexity to be challenging without being overwhelming.

### Domain Model & Entity Definitions

Our domain model maps out an inventory and distribution ecosystem using the following 7 entities:

- **SUPPLIER:** `SupplierID` (PK), `CompanyName`, `ContactName`, `ContactEmail`, `Phone`, `LeadTimeDays`, `MinOrderQty`
- **PRODUCT:** `ProductID` (PK), `SKU`, `Name`, `Category`, `UnitPrice`, `WeightKg`, `Description`
- **INVENTORY:** `InventoryID` (PK), `ProductID` (FK), `LocationID` (FK), `QtyOnHand`, `LastUpdated`, `ReorderPoint`, `ReorderQty`
- **LOCATION:** `LocationID` (PK), `Name`, `Address`, `Type`, `CapacityUnits`
- **ORDER:** `OrderID` (PK), `OrderNumber`, `Status`, `OrderDate`, `TaxAmount`, `ShippingCost`, `ShippingAddress`, `TrackingNumber`, `TotalAmount`, `Notes`
- **ORDER_ITEM:** `OrderItemID` (PK), `OrderNumber` (FK), `ProductID` (FK), `Quantity`, `UnitPrice`, `Subtotal`
- **TRANSFER:** `TransferID` (PK), `FromLocationID` (FK), `ToLocationID` (FK), `ProductID` (FK), `Quantity`, `Status`, `TransferDate`, `Notes`

#### Domain Model ERD

<img width="1020" height="868" alt="Entity Relationship Diagram1" src="https://github.com/user-attachments/assets/b6a76a83-80a6-4294-8fc1-2269d4ad9721" />

### UI Layout Design & Wireframes

Below are the wireframes illustrating our main application pages:

- **Home / Dashboard View:** Features a top banner, a floating navigation bar with a search component, and an evenly tiled category layout utilizing lazy loading.
  <img width="1920" height="1080" alt="HomeScreen" src="https://github.com/user-attachments/assets/c3ce6621-dad3-44b8-a2b6-8b49e07f0cf1" />
- **List View:** Displays active product categories within a tile container paired with a functional filter component
  <img width="1920" height="1080" alt="Category" src="https://github.com/user-attachments/assets/3c2c5746-a595-46e1-b135-d5496c1d3627" />
- **Cart Summary Flow:** Outlines product tiles alongside a transaction subtotal breakdown.
  <img width="1920" height="1080" alt="CartSummary" src="https://github.com/user-attachments/assets/d0a6fa38-2a0f-47d6-b30b-a813b04d6f66" />

* **Login View:** an authentication screen containing a centralized login form within the content container.
  <img width="1920" height="1080" alt="Login" src="https://github.com/user-attachments/assets/45b6375b-d341-45ec-94cd-ff2b03ee44a9" />
