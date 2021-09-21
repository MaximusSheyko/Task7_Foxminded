package com.foxminded.Task7_SQL.service;

import com.foxminded.Task7_SQL.dao.CourseDao;
import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.service.interfaces.Generator;
import com.foxminded.Task7_SQL.utils.Reader;

public class CourseGenerator implements Generator {

    private CourseDao courseDao;
    private Reader reader;
    
    public CourseGenerator(CourseDao courseDao, Reader reader) {
	this.courseDao = courseDao;
	this.reader = reader;
    }

    @Override
    public void generate() {
	var spliterator = "_";

	reader.read("courses.txt").stream().forEach(line -> courseDao
		.save(new Course.CourseBuild()
		.setName(line.split(spliterator)[0])
		.setDescription(line.split(spliterator)[1])
		.build()));
    }
}
