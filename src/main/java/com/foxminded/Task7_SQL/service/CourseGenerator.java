package com.foxminded.Task7_SQL.service;

import com.foxminded.Task7_SQL.dao.CourseJdbcDao;
import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.service.interfaces.Generator;
import com.foxminded.Task7_SQL.utils.Reader;

public class CourseGenerator implements Generator {

    private CourseJdbcDao courseJdbcDao;
    private Reader reader;
    
    public CourseGenerator(CourseJdbcDao courseJdbcDao, Reader reader) {
	this.courseJdbcDao = courseJdbcDao;
	this.reader = reader;
    }

    @Override
    public void generate() {
	var spliterator = "_";

	reader.read("courses.txt").stream().forEach(line -> courseJdbcDao
		.save(new Course.CourseBuild()
		.setName(line.split(spliterator)[0])
		.setDescription(line.split(spliterator)[1])
		.build()));
    }
}
