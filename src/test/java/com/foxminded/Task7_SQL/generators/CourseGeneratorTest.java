package com.foxminded.Task7_SQL.generators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.utils.Reader;

class CourseGeneratorTest {

    private CourseGenerator courseGenerator;
    private Reader reader;

    @BeforeEach
    void setUp() throws Exception {
	reader = new Reader();
	courseGenerator = new CourseGenerator(reader);
    }

    @Test
    void testCourseGenerator_isNoEmptyNoNull() {
	Predicate<List<Course>> coursesIsNoEmptyNoNull = t -> t.isEmpty() && t == null;

	assertFalse(coursesIsNoEmptyNoNull.test(courseGenerator.generate()));
    }

    @Test
    void testGenerate() {
	var coursesSize = 10;

	assertTrue(courseGenerator.generate().size() == coursesSize);
    }

}
