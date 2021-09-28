package com.foxminded.Task7_SQL.generators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.entity.Student;

class RelationshipGeneratorTest {

    private RelationshipGenerator generator;
    private Student student = new Student.StudentBuild()
	    .setFirstName(TEST_NAME)
	    .setLastName(TEST_NAME)
	    .build();
    private static final String TEST_NAME = "TEST";


    @BeforeEach
    void setUp() throws Exception {
	generator = new RelationshipGenerator();
    }

    @Test
    void testGetStudentWithAssignedGroups() {
	var students = new ArrayList<Student>();
	var groups = new ArrayList<Group>();
	var iterator = new AtomicInteger(1);

	Stream.iterate(0, n -> n).limit(50).forEach(iter -> students.add(student));
	Stream.iterate(0, n -> n).limit(5).forEach(iter -> groups.add(new Group.GroupBuilder().setName("Test")
		.setId(iterator.getAndIncrement())
		.build()));

	var result = generator.getStudentWithAssignedGroups(students, groups);

	assertTrue(result.stream().anyMatch(s -> s.getGroupID() != 0));
    }

    @Test
    void testGenerateRandomCourseEachStudent() {
	var students = new ArrayList<Student>();
	var iterator = new AtomicInteger(1);
	var courses = new ArrayList<Course>();
	var sizeOutMap = 10;
	
	Stream.iterate(0, n -> n).limit(10).forEach(iter -> students.add(
		new Student.StudentBuild()
		.setFirstName(TEST_NAME)
		.setLastName(TEST_NAME)
		.setPersonalID(iterator.getAndIncrement())
		.build()));
	Stream.iterate(0, n -> n).limit(10).forEach(iter -> courses.add(
		new Course.CourseBuild()
		.setId(iterator.getAndIncrement())
			.build()));
	
	var outMap = generator.generateRandomCourseEachStudent(students, courses);

	assertFalse(outMap.isEmpty());
	assertEquals(sizeOutMap, outMap.size());

    }

}
