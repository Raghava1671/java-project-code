CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    subject1_marks INT NOT NULL CHECK (subject1_marks >= 0 AND subject1_marks <= 100),
    subject2_marks INT NOT NULL CHECK (subject2_marks >= 0 AND subject2_marks <= 100),
    subject3_marks INT NOT NULL CHECK (subject3_marks >= 0 AND subject3_marks <= 100),
    total INT,
    percentage DOUBLE,
    grade VARCHAR(2)
);
