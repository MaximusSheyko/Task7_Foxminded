package com.foxminded.Task7_SQL.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.service.interfaces.Generator;

public class GroupGenerator implements Generator<Group> {

    @Override
    public List<Group> generate() {
	var random = new Random();
	var groups = new ArrayList<Group>();

	for (var count = 0; count < 10; count++) {
	    var groupIndex = Math.round(10 + Math.random() * 89);
	    var groupName = getRandomChar() + getRandomChar() + "-" + groupIndex;

	    groups.add(new Group.GroupBuilder().setName(groupName).build());
	}

	return groups;
    }

    private String getRandomChar() {
	var random = new Random();

	return String.valueOf((char) (random.nextInt(26) + 'a')).toUpperCase();
    }
}
