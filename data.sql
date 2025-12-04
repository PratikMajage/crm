CREATE DATABASE student_academics_crm;

USE student_academics_crm;

-- Students
CREATE TABLE Students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dob DATE,
    gender ENUM('Male','Female','Other'),
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    department_id INT,
    admission_date DATE,
    status ENUM('Active','Inactive','Graduated','Dropped') DEFAULT 'Active',
    FOREIGN KEY (department_id) REFERENCES Departments(department_id)
);

-- Departments
CREATE TABLE Departments (
    department_id INT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(100) UNIQUE NOT NULL
);

-- Faculty
CREATE TABLE Faculty (
    faculty_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    designation VARCHAR(50),
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES Departments(department_id)
);

-- Courses
CREATE TABLE Courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    credits INT,
    department_id INT,
    faculty_id INT,
    FOREIGN KEY (department_id) REFERENCES Departments(department_id),
    FOREIGN KEY (faculty_id) REFERENCES Faculty(faculty_id)
);

-- Enrollments
CREATE TABLE Enrollments (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date DATE,
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id),
    UNIQUE(student_id, course_id)
);

-- Attendance
CREATE TABLE Attendance (
    attendance_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    attendance_date DATE NOT NULL,
    status ENUM('Present','Absent','Late') NOT NULL,
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

-- Exams
CREATE TABLE Exams (
    exam_id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    exam_type ENUM('Midterm','Final','Quiz','Assignment') NOT NULL,
    exam_date DATE NOT NULL,
    max_marks INT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

-- Results
CREATE TABLE Results (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    exam_id INT NOT NULL,
    student_id INT NOT NULL,
    marks_obtained INT,
    grade VARCHAR(2),
    FOREIGN KEY (exam_id) REFERENCES Exams(exam_id),
    FOREIGN KEY (student_id) REFERENCES Students(student_id)
);

-- Payments
CREATE TABLE Payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATE NOT NULL,
    payment_mode ENUM('Cash','Card','Online','UPI') NOT NULL,
    status ENUM('Pending','Completed','Failed') DEFAULT 'Pending',
    FOREIGN KEY (student_id) REFERENCES Students(student_id)
);

-- 3. Relationships Summary
-- Department ↔ Faculty: One department has many faculty.
-- Department ↔ Students: One department has many students.
-- Faculty ↔ Courses: One faculty teaches many courses.
-- Students ↔ Courses: Many-to-Many via Enrollments.
-- Courses ↔ Exams ↔ Results: One course → many exams → many results.
-- Students ↔ Payments: One student → many payments.
-- Students ↔ CRM Logs: One student → many interactions.

-- Students
INSERT INTO students (first_name, last_name, dob, gender, email, phone, address, department_id, admission_date, status)
VALUES ('John', 'Doe', '2000-01-01', 'Male', 'john.doe@example.com', '1234567890', '123 Street, City', 1, '2023-06-01', 'Active');
INSERT INTO students (first_name, last_name, dob, gender, email, phone, address, department_id, admission_date, status)
VALUES ('Jane', 'Doe', '2001-02-02', 'Female', 'jane.doe@example.com', '1234567891', '456 Street, City', 2, '2023-06-01', 'Active');

-- Departments
INSERT INTO departments (department_name) VALUES ('Computer Science');
INSERT INTO departments (department_name) VALUES ('Mathematics');

-- Faculty
INSERT INTO faculty (first_name, last_name, email, phone, designation, department_id) 
VALUES ('Alice', 'Smith', 'alice.smith@example.com', '9876543210', 'Professor', 1);
INSERT INTO faculty (first_name, last_name, email, phone, designation, department_id) 
VALUES ('Bob', 'Johnson', 'bob.johnson@example.com', '9876543211', 'Lecturer', 2);


-- Courses
INSERT INTO courses (course_code, course_name, credits, department_id, faculty_id)
VALUES ('CS101', 'Introduction to Programming', 4, 1, 1);
INSERT INTO courses (course_code, course_name, credits, department_id, faculty_id)
VALUES ('MATH101', 'Calculus I', 3, 2, 2);

-- Enrollments
INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES (1, 1, '2023-07-01');
INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES (2, 2, '2023-07-01');

-- Attendance
INSERT INTO attendance (student_id, course_id, attendance_date, status) VALUES (1, 1, '2023-09-11', 'Present');
INSERT INTO attendance (student_id, course_id, attendance_date, status) VALUES (2, 2, '2023-09-11', 'Absent');

-- Exams
INSERT INTO exams (course_id, exam_type, exam_date, max_marks) VALUES (1, 'Midterm', '2023-09-20', 100);
INSERT INTO exams (course_id, exam_type, exam_date, max_marks) VALUES (2, 'Midterm', '2023-09-21', 100);

-- Results
INSERT INTO results (exam_id, student_id, marks_obtained, grade) VALUES (1, 1, 90, 'A');
INSERT INTO results (exam_id, student_id, marks_obtained, grade) VALUES (2, 2, 85, 'B');

-- Payments
INSERT INTO payments (student_id, amount, payment_date, payment_mode, status) VALUES (1, 5000.00, '2023-09-11', 'Cash', 'Completed');
INSERT INTO payments (student_id, amount, payment_date, payment_mode, status) VALUES (2, 4500.00, '2023-09-11', 'Card', 'Completed');

-- | Entity      | Method | Endpoint       |
-- | ----------- | ------ | -------------- |
-- | Departments | GET    | `/departments` |
-- | Students    | GET    | `/students`    |
-- | Courses     | GET    | `/courses`     |
-- | Faculty     | GET    | `/faculty`     |
-- | Enrollments | GET    | `/enrollments` |
-- | Attendance  | GET    | `/attendance`  |
-- | Exams       | GET    | `/exams`       |
-- | Results     | GET    | `/results`     |
-- | Payments    | GET    | `/payments`    |

-- Users
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('ADMIN','FACULTY','STUDENT') NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Optional: Link Students/Faculty to Users
ALTER TABLE students ADD COLUMN user_id BIGINT UNIQUE;
ALTER TABLE students ADD CONSTRAINT FK_student_user FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE faculty ADD COLUMN user_id BIGINT UNIQUE;
ALTER TABLE faculty ADD CONSTRAINT FK_faculty_user FOREIGN KEY (user_id) REFERENCES users(user_id);


-- POST /api/users/register → create a new user.
-- POST /api/users/login → validate username & password.


-- | Action        | Method | Endpoint         | Access |
-- | ------------- | ------ | ---------------- | ------ |
-- | Register User | POST   | `/auth/register` | ADMIN  |
-- | Login         | POST   | `/auth/login`    | ALL    |
-- | List Users    | GET    | `/users`         | ADMIN  |
-- | Get Profile   | GET    | `/profile`       | USER   |
