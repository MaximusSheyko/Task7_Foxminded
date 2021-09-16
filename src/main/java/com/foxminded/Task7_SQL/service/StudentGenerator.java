package com.foxminded.Task7_SQL.service.menuquery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.foxminded.Task7_SQL.dao.StudentDao;
import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.service.interfaces.Generator;
import com.foxminded.Task7_SQL.utils.Reader;

public class StudentGenerator implements Generator<Student> {
    
    private StudentDao studentDao;
    private Reader reader;
      
    public StudentGenerator(StudentDao studentDao, Reader reader) {
	this.studentDao = studentDao;
	this.reader = reader;
    }

    @Override
    public void generate() {
	List<String> name = reader.read("First_Name_Student.txt");
	List<String> surname = reader.read("Last_Name_Student.txt");
	var bound = 0;
	var random = new Random();

	bound = name.size() - 1;

	for (int i = 1; 200 >= i; i++) {
	    try {
		studentDao.save(new Student
			.StudentBuild()
			.setFirstName(name.get(random.nextInt(bound)))
			.setLastName(surname.get(random.nextInt(bound)))
			.build());
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

}
