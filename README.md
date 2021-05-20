# Project 1 : Expense Reimbursement System
Project1 Reimbursement WebApp project files

## Project Description
The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. 
All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. 
Finance managers can log in and view all reimbursement requests and past history for all employees in the company. 
Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used
- Java - version 1.8.0_281
- JavaScript ES6
- HTML5
- CSS3
- Javalin - v3.13.5
- MariaDB - v2.7.2
- Hibernate - v5.4.30.Final
- Logback - v1.2.3
- Jackson - v2.12.3
- JUnit - v4.13.2
- Mockito - v3.9.0
- Bootstrap - v5.0.1
- D3.js - v4.13.0

## Features
List of features ready and TODOs for future development
- User Session Management
- Submit Reimbursement request
- Filter Reimbursement requests by status
- Manager approve/deny actions for pending reimbursements requests
- Expense overview graph

To-do list:
- Sign-up for new users
- Implement admin user to have control post deployment

## Getting Started
```git clone git@github.com:bsarvaiya24/project1biren.git```
The code in the **src** folder of this repository hold the source code for this project.
It can be build using Eclipse or Spring Tools Suite.
The project includes Javalin which will create a jetty server and serve the project on a localhost link.
You can access this link your browser to interact with the project.

## Usage
There are 5 predefined users in this system.
The username and password for them can be found in the `user_id_password.txt` file

There are 2 types of Users: Submitter & Approver

Based on the login credentials you can access each type of user interface and perform different operations allowed for that user.
