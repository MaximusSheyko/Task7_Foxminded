package com.foxminded.Task7_SQL.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.foxminded.Task7_SQL.dao.CourseDao;
import com.foxminded.Task7_SQL.dao.GroupDao;
import com.foxminded.Task7_SQL.dao.StudentDao;
import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.entity.Student;

public class GeneratorData {
    private StudentDao studentDao;
    private CourseDao courseDao;
    private GroupDao groupDao;
    
    public GeneratorData(StudentDao studentDao, CourseDao courseDao, 
	    GroupDao groupDao) {
	this.studentDao = studentDao;
	this.courseDao = courseDao;
	this.groupDao = groupDao;
    }
    
    public void generateGroups() {
	Random random = new Random();
	
	for(var count = 0; count < 10; count++) {
	    var groupIndex = Math.round(10 + Math.random() * 89);
	    var groupName = getRandomChar() + getRandomChar() +
		    "-" + groupIndex;
	    try {
		groupDao.save(new Group.GroupBuilder()
			.setName(groupName)
			.build());
	    } catch (SQLException ex) {
		ex.printStackTrace();
	    }
	}
    }
    
    public void generateCourses() {
	var spliterator = "_";
	
	try {
	    readLine("Courses.txt").stream().forEach(line -> courseDao.save(
	    	 new Course.CourseBuild()
	    	.setName(line.split(spliterator)[0])
	    	.setDescription(line.split(spliterator)[1])
	    	.build()));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
    }  
    
    public void generateStudents() {
	List<String> name = new ArrayList<>();
	List<String> surname = new ArrayList<>();
	var bound = 0;
	var random = new Random();
	
	try{ 
	    name = readLine("First_Name_Student.txt");
	    surname = readLine("Last_Name_Student.txt");
	    bound = name.size() - 1;
	}catch(IOException ex) {
	    ex.getStackTrace();
	}
	
	for(int i = 1; 200 >= i; i++) {
	    try {
		studentDao.save(new Student.StudentBuild()
				.setFirstName(name.get(random.nextInt(bound)))
				.setLastName(surname.get(random.nextInt(bound)))
				.build());
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
	
	
    }
    
    private String getRandomChar() {
	Random rand = new Random();
	return String.valueOf((char)(rand.nextInt(26) + 'a')).toUpperCase();
    }
    
    private List<String> readLine(String fileName) throws IOException{
	return Files.readAllLines(new File(Objects.requireNonNull(ClassLoader
		.getSystemClassLoader()
		.getResource(fileName)
		.getFile()))
		.toPath()
		.toAbsolutePath());
    }
}
