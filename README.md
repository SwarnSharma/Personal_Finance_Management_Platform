📊 # Personal Finance Management System

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

User registration and login

Role-based access (User / Admin)

Secure session management and logout

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

View all registered users

Secure user deletion

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

🧱 System Architecture

3-Tier Architecture

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
Loose coupling, scalability, maintainability

🔄 Transaction Management

AutoCommit disabled for critical financial operations

Commit executed only after all operations succeed

Rollback performed on any failure

Applied in goal savings and budget-related operations

Ensures:
Atomicity, Consistency, Reliability

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
Design Rationale

Normalized tables to avoid redundancy

Foreign key constraints to maintain relationships

Cascade deletes for data integrity

Separate savings history table for audit trail

Key Tables

users

expenses

budgets

goals

goal_savings

🚧 Key Challenges Faced & Solutions
Challenge	Solution
Oversaving goals	Server-side validation with rollback
Partial database updates	JDBC transaction management
Table not found errors	Centralized SQL schema
SQL injection risk	PreparedStatement usage
🚀 Innovation / Extra Effort

Oversave prevention logic

Auto goal achievement detection

Savings history tracking

Clean DAO-based architecture

Robust exception handling

Transaction-safe financial updates

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

Import project into Eclipse or IntelliJ

Configure Apache Tomcat server

Run the provided SQL file to create database and tables

Update database credentials in DBConnection.java

Deploy and run on server

Access URL:
http://localhost:8080/PersonalFinanceManager/

📌 Expected Outcomes

Zero partial financial transactions

Accurate budget and goal tracking

Improved financial discipline for users

Scalable and maintainable system design

🏁 Conclusion

The Personal Finance Management System provides a robust, secure, and scalable solution for managing personal finances. By following industry-standard practices such as MVC architecture, DAO pattern, transaction management, and strong validation, the project demonstrates both technical proficiency and practical problem-solving skills.
