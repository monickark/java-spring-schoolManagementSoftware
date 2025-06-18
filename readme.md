# School Management Software (Java Spring)

A full-featured school management system built using Java, Spring Framework, and JSP. The software enables schools to manage students, attendance, timetable, exams, fees, and more in a modular and efficient way.

---

## 🛠 Technologies Used

- Java (JDK 1.8+)
- Spring Framework (MVC, DAO)
- JSP/Servlets
- Apache Tomcat
- Bootstrap (UI)
- MySQL (for persistence)

---

## 📚 Core Modules & Features

### 🎓 Student Management Module
- Register new students with full profile details
- Assign students to classes and sections
- Update student profiles and track academic history
- Filter/search student records by class, name, ID
- Export/import student data in bulk
- Mark students as active, alumni, or transferred

### 🟩 Attendance Module
- Record daily attendance by class or individual student
- View and edit attendance logs with timestamps
- Generate attendance reports for a student or class
- Support for absence reasons and attachments (e.g., medical note)
- Upload attendance from Excel/CSV
- Integrate with notification systems and reports

### ⏰ Timetable Module
- Build and manage weekly class timetables
- Assign subjects and teachers to periods
- View personalized timetables for each teacher
- Configure period duration and daily structure
- Detect conflicts in teacher or room assignments
- Handle exceptions: holidays, events, special classes

### 📝 Examination Module
- Schedule exams for all classes and subjects  
- Configure exam types (Unit Test, Mid-Term, Final)  
- Enter and update student marks (theory & practical)  
- Calculate grades and results using custom rules  
- Generate printable report cards per student  
- Export performance reports (class-wise, subject-wise)  
- Handle mark revisions and result corrections

### 💰 Fee Management Module
- Define fee heads (tuition, transport, library, etc.)
- Assign fee structures by class or student category
- Collect payments via cash, cheque, bank transfer, or online
- Generate receipts and track full or partial payments
- View student-wise fee ledger and defaulter list
- Export fee collection reports and audit logs
- Send reminders for pending dues

---

## 🚀 Setup Instructions
- Import project into Eclipse as an existing Java EE project.
- Configure Apache Tomcat server.
- Set up MySQL database and update DAO config.
- Deploy and run:
    http://localhost:8080/java-spring-schoolManagementSoftware/

---

## 📃 License
Free for educational and organizational use.