📊 Personal Finance Management System

A Java JSP-based Web Application


📖 Project Description

The Personal Finance Management System is a secure web-based application that helps users manage expenses, budgets, and financial goals efficiently. Built using Java, JSP, Servlets, JDBC, and MySQL, the system ensures data consistency, transaction safety, and a clean user-friendly interface while following MVC and DAO architecture principles.


❓ Problem Statement

Manual expense tracking is error-prone and inefficient

No centralized platform for managing budgets and goals

Traditional systems lack transaction safety

Risk of data inconsistency during financial updates

Need for a secure, reliable, and user-friendly finance management system


🎯 Project Objectives

Provide a centralized system for personal finance tracking

Enable accurate expense, budget, and goal management

Ensure atomic financial transactions using JDBC

Prevent data inconsistency using commit and rollback

Maintain relational integrity using foreign keys

Implement secure authentication and session handling


✨ System Features
🔐 Authentication & Security

--> User registration and login

--> Role-based access (User / Admin)

--> Secure session management and logout


💸 Expense Management

Add, view, and delete expenses

Category-based tracking

Expense summary on dashboard


📊 Budget Management

Add, view, and delete budgets

Date-based budget planning

Budget safety indication


🎯 Goal Management

Create financial goals with target amount and deadline

Add savings incrementally

Oversave prevention logic

Automatic goal achievement detection

Visual progress bar

Savings history tracking


📈 Dashboard

Total expenses overview

Total budget summary

Total goals count

Graphical visualization using charts


🧩 Admin Functionalities

-> View all registered users

-> Secure user deletion



🛠️ Technologies Used


Frontend

JSP

HTML5

CSS3

Chart.js

Backend


Java (Servlets)

JDBC

DAO Pattern

MVC Architecture

Database

MySQL

Server

Apache Tomcat


🧱 System Architecture: 3-Tier Architecture

Presentation Layer

JSP pages

CSS for styling

Business Layer

Servlets

DAO pattern for database operations

Data Layer

MySQL database

JDBC connectivity

Benefits:
-> Loose coupling, scalability, maintainability


🔄 Transaction Management

-> AutoCommit disabled for critical financial operations

-> Commit executed only after all operations succeed

-> Rollback performed on any failure

-> Applied in goal savings and budget-related operations

Ensures:
-> Atomicity, Consistency, Reliability


✔ Validation Strategy

Client-Side Validation

Required field checks

Amount must be greater than zero

Server-Side Validation

User session verification

Goal ownership validation

Oversave prevention

Secure request handling


🗄️ Database Design

-> Design Rationale

-> Normalized tables to avoid redundancy

-> Foreign key constraints to maintain relationships

-> Cascade deletes for data integrity

-> Separate savings history table for audit trail


Key Tables

users

expenses

budgets

goals

goal_savings


🚧 Key Challenges Faced & Solutions

1. Challenge	Solution
2. Oversaving goals	Server-side validation with rollback
3. Partial database updates	JDBC transaction management
4. Table not found errors	Centralized SQL schema
5. SQL injection risk	PreparedStatement usage


🚀 Innovation / Extra Effort

1. Oversave prevention logic

2. Auto goal achievement detection

3. Savings history tracking

4. Clean DAO-based architecture

5. Robust exception handling

6. Transaction-safe financial updates


📂 Project Structure

PersonalFinanceManager/
├── src/main/java
│   ├── com.finance.dao
│   ├── com.finance.model
│   ├── com.finance.servlet
│   └── com.finance.db
│
├── src/main/webapp
│   ├── css/style.css
│   ├── index.jsp
│   ├── register.jsp
│   ├── dashboard.jsp
│   ├── expenses.jsp
│   ├── budgets.jsp
│   ├── goals.jsp
│   ├── admin_dashboard.jsp
│   └── logout.jsp
│
└── README.md


🚀 How to Run the Project

1. Import project into Eclipse or IntelliJ

2. Configure Apache Tomcat server

3. Run the provided SQL file to create database and tables

4. Update database credentials in DBConnection.java

5. Deploy and run on server

6. Access URL: http://localhost:8080/PersonalFinanceManager/
   

📌 Expected Outcomes

-> Zero partial financial transactions

-> Accurate budget and goal tracking

-> Improved financial discipline for users

-> Scalable and maintainable system design


🏁 Conclusion

The Personal Finance Management System provides a robust, secure, and scalable solution for managing personal finances. By following industry-standard practices such as MVC architecture, DAO pattern, transaction management, and strong validation, the project demonstrates technical proficiency and practical problem-solving skills.


📸 Screenshots

<img width="1280" height="1392" alt="Screenshot 2026-01-05 222333" src="https://github.com/user-attachments/assets/302a2c29-697a-421e-87c5-1ccdc314df28" />

<img width="1280" height="1392" alt="Screenshot 2026-01-05 222501" src="https://github.com/user-attachments/assets/a69f8122-c857-42b9-9541-73c93318218d" />

<img width="1280" height="1392" alt="Screenshot 2026-01-05 223912" src="https://github.com/user-attachments/assets/82d636a3-6ddc-4974-a928-711ca258a5b0" />

<img width="1280" height="1392" alt="Screenshot 2026-01-05 223944" src="https://github.com/user-attachments/assets/1ef1e023-f0fa-4a87-a8fd-633cbf85bece" />

<img width="2201" height="965" alt="image" src="https://github.com/user-attachments/assets/4e41259a-e470-42b8-9fd7-70a1a52f1338" />

<img width="1280" height="1392" alt="image" src="https://github.com/user-attachments/assets/787f0166-0492-447c-a489-6f06435270bd" />

<img width="1280" height="1392" alt="Screenshot 2026-01-05 234343" src="https://github.com/user-attachments/assets/86cebffb-b874-4bc3-b722-3b8bce7dd71f" />


