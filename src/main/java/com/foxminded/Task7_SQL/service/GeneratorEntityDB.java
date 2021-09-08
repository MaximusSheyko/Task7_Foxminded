package com.foxminded.Task7_SQL.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import com.foxminded.Task7_SQL.dao.CourseDao;
import com.foxminded.Task7_SQL.dao.GroupDao;
import com.foxminded.Task7_SQL.dao.StudentDao;
import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.utils.ReaderResources;

import static com.foxminded.Task7_SQL.utils.ReaderResources.*;

public class GeneratorEntityDB {
    private StudentDao studentDao;
    private CourseDao courseDao;
    private GroupDao groupDao;
    ReaderResources rd = new ReaderResources();
    
    public GeneratorEntityDB(StudentDao studentDao, CourseDao courseDao, 
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
	
	readFile("Courses.txt").stream().forEach(line -> courseDao.save(
		 new Course.CourseBuild()
		.setName(line.split(spliterator)[0])
		.setDescription(line.split(spliterator)[1])
		.build()));
	
    }  
    
    public void generateStudents() {
	List<String> name = new ArrayList<>();
	List<String> surname = new ArrayList<>();
	var bound = 0;
	var random = new Random();
	 
	name = readFile("First_Name_Student.txt");
	surname = readFile("Last_Name_Student.txt");
	bound = name.size() - 1;
	
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
    
    public void  assignStudentsToGroups() throws SQLException {
	var random = new Random();
	
	ArrayList<Student> students = studentDao.getAllData();
	List<Group> groups = groupDao.getAllData();
	ListIterator<Student> studentsId = students.listIterator();

	for(var group : groups) {
	    int countStudents = 10 + random.nextInt(30 - 10 + 1);
	    var minStudent = 3; 
	    var counter = 0;
	    int quantityElements = getQuantityElements(studentsId);
	    
	    if (quantityElements < minStudent) {
	            break;
		}
	
	    while (counter < countStudents && studentsId.hasNext()) {
		var student = studentsId.next();
		student.setGroupId(group.getId());
		studentDao.update(student);
		studentsId.remove();
		counter++;
	    }
	}
    }
   
    private static<T> int getQuantityElements(ListIterator<T> iterators) {
        var count = 0;
	
	while(iterators.hasNext()) {
	    iterators.next();
	    count ++;
	}
	
	while(iterators.hasPrevious()) {
	    iterators.previous();
	}
	
	return count;
    }
    
    private String getRandomChar() {
  	Random rand = new Random();
  	
  	return String.valueOf((char)(rand.nextInt(26) + 'a')).toUpperCase();
    }
}
