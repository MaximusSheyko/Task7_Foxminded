package com.foxminded.Task7_SQL.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.entity.Student;

public class RelationshipGenerator {

    public List<Student> getStudentWithAssignedGroups(List<Student> students,
	    List<Group> groups) {
	var random = new Random();
	var groupsId = groups.stream().map(Group::getId).toList();
	var updatedStudents = new ArrayList<Student>();
	Collections.shuffle(students);
	var studentsId = students.listIterator();

	for (var id : groupsId) {
	    var randomIteration = 10 + random.nextInt(30 - 10 + 1);
	    var count = 0;

	    if (getQuntityStudentsId(studentsId) < 10) {
		break;
	    }

	    while (studentsId.hasNext() && count < randomIteration) {
		var student = studentsId.next();
		student.setGroupId(id);
		updatedStudents.add(student);
		count++;
		studentsId.remove();
	    }
	}

	return updatedStudents;
    }

    private <T> int getQuntityStudentsId(ListIterator<T> studentsId) {
	var counter = 0;

	while (studentsId.hasNext()) {
	    studentsId.next();
	    counter++;
	}

	while (studentsId.hasPrevious()) {
	    studentsId.previous();
	}

	return counter;
    }

    public Map<Integer, List<Integer>> generateRandomCourseEachStudent(List<Student> students,
	    List<Course> courses) {
	Map<Integer, List<Integer>> studentsIdTheirCoursesId = new HashMap<>();
	var studentsId = students.stream().map(Student::getPersonalID).toList();
	var coursesId = courses.stream().map(Course::getId).toList();

	studentsId.stream()
		.forEach(id -> studentsIdTheirCoursesId.put(id, getRandomCourses(coursesId)));
	
	return studentsIdTheirCoursesId;
    }

    private List<Integer> getRandomCourses(List<Integer> coursesId) {
	var random = new Random();
	Set<Integer> cache = new HashSet<>();
	var currentCoursesId = new ArrayList<Integer>();
	int iterations = 1 + random.nextInt(3);

	for (var i = 0; i < iterations; i++) {
	    var idCourse = coursesId.get(random.nextInt(coursesId.size()));

	    if (!cache.contains(idCourse)) {
		currentCoursesId.add(idCourse);
		cache.add(idCourse);
	    }
	}

	return currentCoursesId;
    }
}
