
A simple Java-based console application for managing a library's books and members. It supports adding books, searching the catalog, registering members, borrowing and returning books, tracking due dates, calculating overdue fines, and sending notifications through SMS and email.

## Project Overview

This project is designed as a beginner-friendly library system that stores data in local text files instead of a database. It is suitable for learning Java file handling, object-oriented programming, and basic workflow automation in a library context.

## Features

- Add new books to the library catalog
- View all books
- Search books by ISBN, title, or author
- Add new library members
- View member details
- Borrow books from the library
- Return books and update availability
- Track due dates and overdue status
- Calculate daily fines for overdue books
- Send SMS and email notifications for returns and overdue notices

## Project Structure

- LibraryManagementSystem.java - main entry point and menu-driven interface
- Book.java - book details and file operations
- Member.java - member registration and data storage
- Loan.java - borrowing/return logic, search, fine calculation, and notifications
- LibraryEntity.java - common interface for library entities
- EmailSender.java - SendGrid email integration
- SmsSender.java - Twilio SMS integration

## Prerequisites

Before running the project, make sure you have:

- Java JDK 8 or newer
- A terminal or command prompt
- Twilio account credentials for SMS sending
- SendGrid API key and sender email for email sending

## Setup

1. Open a terminal in the project folder.
2. Make sure Java is installed and available in PATH.
3. Update the placeholders in the following files:
   - EmailSender.java: replace <YOUR API KEY> and <YOUR EMAIL>
   - SmsSender.java: replace <YOUR ACCOUNT SID>, <YOUR AUTHENTICATION TOKEN>, and <YOUR TWILIO PHONE NUMBER>

## How to Run

Compile the project:

javac *.java

Run the application:

java LibraryManagementSystem

## Usage

When the program starts, you can choose between:

- Librarian
- Member

The librarian menu allows operations such as:

- Add books
- View books
- Search books
- Add members
- View member details

The member menu allows:

- View all books
- Search a book
- View member details
- Borrow a book
- Return a book

## Data Storage

The application stores records in text files such as:

- bookDetail.txt - all book records
- <library_number>.txt - each member's profile and borrowing history

## Notes

- This project uses plain text files rather than a database, so it is best suited for learning and small-scale demonstration.
- The app expects valid Twilio and SendGrid credentials to be configured before sending real notifications.
- The project is a console-based application and does not include a graphical user interface.

## License

This project is provided for educational use and can be modified or extended as needed.
