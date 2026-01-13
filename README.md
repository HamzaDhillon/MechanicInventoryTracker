# 🛠️ Mechanic Inventory Management System (MIMS)
**A Full-Stack Enterprise Automotive Solution**

Developed for the **CIS2232 - Advanced Object-Oriented Programming** course at Holland College. This project demonstrates a complete software development lifecycle (SDLC) using **Agile Sprints**, **Jira Issue Tracking**, and **Version Control**.

---

## 👥 Project Team & Roles
* **Hamza Tariq (Lead Developer):** Full-stack implementation, API architecture, and Database logic.
* **John Stewart (Business Analyst):** Requirements gathering, color palette, and UAT.
* **Elizabeth Eagles (Project Manager/QA):** Sprint tracking and milestone validation.

---

## 🚀 Advanced Technical Features

### 📡 Multi-Protocol API Integration
The system provides dual-layer connectivity for third-party integrations:
* **REST API (Jersey):** Full CRUD operations (GET, POST, DELETE) returning JSON responses with standard HTTP status codes.
* **SOAP Web Service:** A Java-based SOAP service providing contract-based entity retrieval.
* **Client Implementation:** Developed a client application to consume and test internal services.

### 💾 Data Persistence & Architecture
We utilized two distinct data access patterns to demonstrate versatility:
* **Spring Data JPA:** Used for standard CRUD operations and entity validation.
* **JDBC & DAO Pattern:** Leveraged for complex financial reporting logic and high-performance data retrieval.
* **Relational Schema:** Optimized MySQL database using a strict naming convention.

### 📊 Reporting & File I/O
* **Dynamic Reports:** Real-time financial snapshots filtered by date ranges or Part Numbers.
* **Physical Logging:** The system generates timestamped `.txt` audit files (e.g., `InventoryReport_20260113.txt`) saved to `C:\cis2232\`.

### 🧪 Quality Assurance
* **Unit Testing:** JUnit tests implemented to verify the accuracy of the calculation engine.
* **Business Validation:** Custom logic to prevent future-dated transactions and negative inventory values.

---

## 🎨 Theme: "Midnight Garage"
The UI follows an industrial, high-contrast aesthetic:
* **Primary:** `#000000` (Stealth Black) 
* **Secondary:** `#9c9a9a` (Metallic Grey)
* **Accent:** `#fcba03` (Caution Yellow)

---

## 📅 Development Roadmap (Sprints)
- **Sprint 1:** Repository setup, Jira integration, and UI implementation.
- **Sprint 2:** JDBC reporting functionality and File I/O implementation.
- **Sprint 3:** Spring MVC, JPA implementation, and Spring Validation.
- **Sprint 4:** REST & SOAP Web Service architecture.
- **Sprint 5:** Unit Testing, Business Logic refinement, and Finalization.

---

## 🛠️ Tech Stack
* **Language:** Java 8+
* **Frameworks:** Spring Boot (MVC, Data JPA), Jersey
* **Database:** MySQL
* **Testing:** JUnit
* **Project Management:** Jira / Bitbucket

---

## ⚙️ Installation & Setup
1. **Clone the repository:**
   ```bash
   git clone [https://github.com/HamzaDhillon/MechanicInventoryTracker.git](https://github.com/HamzaDhillon/MechanicInventoryTracker.git)

### UI Preview

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/742c121f-7e06-40d2-8436-746c1f6aaa42" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/0d5b7a31-e261-42b5-8d90-87b26a8286a1" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/053fc72c-92aa-4bf0-bcdc-04b5e415676f" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/3d133717-3187-4bb6-857e-b2cb156108ba" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/5c125d5c-2829-4d3c-88c7-7d72f6c82ae1" />





