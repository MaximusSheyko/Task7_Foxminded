package com.foxminded.Task7_SQL.generators;

import java.util.ArrayList;
import java.util.List;

import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.service.interfaces.Generator;
import com.foxminded.Task7_SQL.utils.Reader;

public class CourseGenerator implements Generator<Course> {

    private Reader reader;

    public CourseGenerator(Reader reader) {
	this.reader = reader;
    }

    @Override
    public List<Course> generate() {
	var spliterator = "_";
	var courses = new ArrayList<Course>();

	reader.read("courses.txt").stream().forEach(course -> courses
		.add(new Course.CourseBuild()
			.setName(course.split(spliterator)[0])
			.setDescription(course.split(spliterator)[1])
			.build()));

	return courses;
    }
}
