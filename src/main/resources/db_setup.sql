DROP TABLE IF EXISTS students_courses CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS courses CASCADE;

CREATE TABLE groups(
    group_Id SERIAL,
    groupName VARCHAR(255),
    PRIMARY KEY (group_Id)
);

CREATE TABLE students(
    student_Id SERIAL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    group_Id INTEGER,
    PRIMARY KEY (student_Id),
    FOREIGN KEY (group_Id) REFERENCES groups(group_Id)
);

CREATE TABLE courses(
    course_Id SERIAL,
    courseName VARCHAR(255),
    courseDescription TEXT,
    PRIMARY KEY (course_Id)
);

CREATE TABLE students_courses(
    student_Id INTEGER REFERENCES students(student_Id) ON DELETE CASCADE,
    course_Id INTEGER REFERENCES courses(course_Id) ON DELETE CASCADE,
    UNIQUE (student_id, course_id)
);
