package com.foxminded.Task7_SQL.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.foxminded.Task7_SQL.dao.StudentDao;
import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.service.interfaces.Generator;
import com.foxminded.Task7_SQL.utils.Reader;

public class StudentGenerator implements Generator {
    
    private StudentDao studentDao;
    private Reader reader;
      
    public StudentGenerator(StudentDao studentDao, Reader reader) {
	this.studentDao = studentDao;
	this.reader = reader;
    }

    @Override
    public void generate() {
	var name = reader.read("firstname_student.txt");
	var surname = reader.read("lastmame_student.txt");
	var bound = 0;
	var random = new Random();

	bound = name.size() - 1;

	for (var i = 1; 200 >= i; i++) {
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
