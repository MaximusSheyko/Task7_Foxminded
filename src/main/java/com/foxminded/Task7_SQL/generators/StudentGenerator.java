package com.foxminded.Task7_SQL.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.service.interfaces.Generator;
import com.foxminded.Task7_SQL.utils.Reader;

public class StudentGenerator implements Generator<Student> {

    private Reader reader;

    public StudentGenerator(Reader reader) {
	this.reader = reader;
    }

    @Override
    public List<Student> generate() {
	var name = reader.read("firstname_student.txt");
	var surname = reader.read("lastmame_student.txt");
	var students = new ArrayList<Student>();
	var random = new Random();
	var bound = name.size() - 1;

	for (var i = 1; 200 >= i; i++) {
	    students.add(new Student.StudentBuild()
		    .setFirstName(name.get(random.nextInt(bound)))
		    .setLastName(surname.get(random.nextInt(bound)))
		    .build());
	}

	return students;
    }
}
