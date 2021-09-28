package com.foxminded.Task7_SQL.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Group;

class GroupGeneratorTest {

    private GroupGenerator groupGenerator;

    @BeforeEach
    void setUp() throws Exception {
	groupGenerator = new GroupGenerator();
    }

    @Test
    void testGenerateIsNoEmptyNoNull() {
	Predicate<List<Group>> groupsIsNoEmptyNoNull = t -> t.isEmpty() && t == null;

	assertFalse(groupsIsNoEmptyNoNull.test(groupGenerator.generate()));
    }

    void testGenerate_sizeOut() {
	var groupsSize = 20;

	assertEquals(groupsSize, groupGenerator.generate().size());
    }

}
