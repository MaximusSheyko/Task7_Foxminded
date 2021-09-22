package com.foxminded.Task7_SQL.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.foxminded.Task7_SQL.dao.StudentJdbcDao;
import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.service.interfaces.Generator;
import com.foxminded.Task7_SQL.utils.Reader;

public class StudentGenerator implements Generator {
    
    private StudentJdbcDao studentJdbcDao;
    private Reader reader;
      
    public StudentGenerator(StudentJdbcDao studentJdbcDao, Reader reader) {
	this.studentJdbcDao = studentJdbcDao;
	this.reader = reader;
    }

    @Override
    public void generate() {
	var name = reader.read("firstname_student.txt");
	var surname = reader.read("lastmame_student.txt");
	var random = new Random();
	var bound = name.size() - 1;

	for (var i = 1; 200 >= i; i++) {
	    try {
		studentJdbcDao.save(new Student
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
