package com.foxminded.Task7_SQL.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.utils.Reader;

class StudentGeneratorTest {

    private StudentGenerator studentGenerator;
    private Reader reader;

    @BeforeEach
    void setUp() throws Exception {
	reader = new Reader();
	studentGenerator = new StudentGenerator(reader);
    }

    @Test
    void testGenerate_isNoEmptyNoNull() {
	Predicate<List<Student>> studentsIsNoEmptyNoNull = t -> t.isEmpty() && t == null;

	assertFalse(studentsIsNoEmptyNoNull.test(studentGenerator.generate()));
    }

    @Test
    void testGenerate_outSize() {
	var studentsSize = 200;

	assertEquals(studentsSize, studentGenerator.generate().size());
    }

}
