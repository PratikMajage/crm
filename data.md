## 1. **Users / Authentication**

You already have a `User` entity for authentication. Let’s make it scalable for roles.

**Table: `users`**

| Column         | Type                              | Notes            |
| -------------- | --------------------------------- | ---------------- |
| id             | BIGINT (PK)                       | Auto-increment   |
| username       | VARCHAR(50)                       | Unique           |
| email          | VARCHAR(100)                      | Unique           |
| password\_hash | VARCHAR(255)                      | Hashed password  |
| role           | ENUM('STUDENT','FACULTY','ADMIN') | Role of user     |
| created\_at    | TIMESTAMP                         | Default now()    |
| updated\_at    | TIMESTAMP                         | Update on change |

---

## 2. **Student Profile**

Students need additional info separate from authentication:

**Table: `students`**

| Column         | Type        | Notes              |
| -------------- | ----------- | ------------------ |
| id             | BIGINT (PK) | FK to `users.id`   |
| first\_name    | VARCHAR(50) |                    |
| last\_name     | VARCHAR(50) |                    |
| dob            | DATE        | Date of birth      |
| enrollment\_no | VARCHAR(50) | Unique for student |
| course\_id     | BIGINT      | FK to `courses.id` |
| created\_at    | TIMESTAMP   | Default now()      |
| updated\_at    | TIMESTAMP   |                    |

---

## 3. **Faculty Profile**

**Table: `faculty`**

| Column       | Type        | Notes            |
| ------------ | ----------- | ---------------- |
| id           | BIGINT (PK) | FK to `users.id` |
| first\_name  | VARCHAR(50) |                  |
| last\_name   | VARCHAR(50) |                  |
| employee\_no | VARCHAR(50) | Unique           |
| department   | VARCHAR(50) |                  |
| created\_at  | TIMESTAMP   | Default now()    |
| updated\_at  | TIMESTAMP   |                  |

---

## 4. **Courses**

Courses are shared between students and faculty.

**Table: `courses`**

| Column      | Type         | Notes              |
| ----------- | ------------ | ------------------ |
| id          | BIGINT (PK)  |                    |
| name        | VARCHAR(100) |                    |
| code        | VARCHAR(20)  | Unique             |
| description | TEXT         | Optional           |
| faculty\_id | BIGINT       | FK to `faculty.id` |
| created\_at | TIMESTAMP    | Default now()      |
| updated\_at | TIMESTAMP    |                    |

---

## 5. **Attendance**

Track attendance for each student in each class.

**Table: `attendance`**

| Column      | Type                            | Notes               |
| ----------- | ------------------------------- | ------------------- |
| id          | BIGINT (PK)                     |                     |
| student\_id | BIGINT                          | FK to `students.id` |
| course\_id  | BIGINT                          | FK to `courses.id`  |
| date        | DATE                            |                     |
| status      | ENUM('PRESENT','ABSENT','LATE') |                     |
| created\_at | TIMESTAMP                       | Default now()       |
| updated\_at | TIMESTAMP                       |                     |

---

## 6. **Exams / Assessments**

**Table: `exams`**

| Column      | Type         | Notes              |
| ----------- | ------------ | ------------------ |
| id          | BIGINT (PK)  |                    |
| course\_id  | BIGINT       | FK to `courses.id` |
| name        | VARCHAR(100) | Exam name          |
| date        | DATE         |                    |
| max\_marks  | INT          |                    |
| created\_at | TIMESTAMP    | Default now()      |
| updated\_at | TIMESTAMP    |                    |

**Table: `exam_results`**

| Column          | Type         | Notes               |
| --------------- | ------------ | ------------------- |
| id              | BIGINT (PK)  |                     |
| exam\_id        | BIGINT       | FK to `exams.id`    |
| student\_id     | BIGINT       | FK to `students.id` |
| marks\_obtained | DECIMAL(5,2) |                     |
| grade           | VARCHAR(5)   | Optional            |
| created\_at     | TIMESTAMP    | Default now()       |
| updated\_at     | TIMESTAMP    |                     |

---

## 7. **Payments / Fees**

**Table: `payments`**

| Column        | Type                         | Notes               |
| ------------- | ---------------------------- | ------------------- |
| id            | BIGINT (PK)                  |                     |
| student\_id   | BIGINT                       | FK to `students.id` |
| amount        | DECIMAL(10,2)                |                     |
| status        | ENUM('PAID','DUE','PARTIAL') |                     |
| payment\_date | DATE                         |                     |
| created\_at   | TIMESTAMP                    | Default now()       |
| updated\_at   | TIMESTAMP                    |                     |

---

## 8. **Notifications**

**Table: `notifications`**

| Column      | Type                            | Notes            |
| ----------- | ------------------------------- | ---------------- |
| id          | BIGINT (PK)                     |                  |
| user\_id    | BIGINT                          | FK to `users.id` |
| message     | TEXT                            |                  |
| type        | ENUM('INFO','ALERT','REMINDER') |                  |
| is\_read    | BOOLEAN                         | Default false    |
| created\_at | TIMESTAMP                       | Default now()    |

---

## 9. **Relationships**

* `users` → `students` / `faculty` (1:1)
* `students` → `courses` (Many-to-One via `course_id`)
* `faculty` → `courses` (1\:Many)
* `students` → `attendance` (1\:Many)
* `courses` → `attendance` (1\:Many)
* `students` → `exam_results` (1\:Many)
* `exams` → `exam_results` (1\:Many)
* `students` → `payments` (1\:Many)
* `users` → `notifications` (1\:Many)

---

### ✅ Benefits of this schema

1. **Scalable**: Adding new roles (like Admin, Staff) is easy.
2. **Separation of Concerns**: User authentication is separate from student/faculty profiles.
3. **Extensible**: Adding new modules like “Assignments”, “Library”, “Grades Analytics” is straightforward.
4. **Normalized**: Avoids redundant data.

---
