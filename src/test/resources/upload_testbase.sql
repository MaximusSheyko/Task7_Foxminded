INSERT INTO groups 
VALUES (default, 'MV-11'),
(default, 'MV-12'),
(default, 'MV-13');

INSERT INTO students (student_Id, firstname, lastname, group_id) 
VALUES (default, 'Maxim', 'Sheyko', 1), 
(default, 'Vladymir', 'Fisher', 1), 
(default, 'Karen', 'Teilor', 2);

INSERT INTO courses
VALUES (default, 'Math', 'Something about the Math'),
(default, 'Philosophy', 'Something about the Philosophy');

INSERT INTO students_courses
VALUES (1,2),(1,1),(2,1);