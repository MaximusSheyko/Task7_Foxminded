package com.foxminded.Task7_SQL.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.foxminded.Task7_SQL.dao.DAOException;
import com.foxminded.Task7_SQL.dao.GroupJdbcDao;
import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.service.menuquery.GroupService;

@RunWith(MockitoJUnitRunner.class)
class GroupServiceTest {

    @Mock
    private GroupJdbcDao dao;

    @InjectMocks
    private GroupService service;

    @BeforeEach
    void setUp() throws Exception {
	dao = mock(GroupJdbcDao.class);
	service = new GroupService(dao);
    }

    @Test
    void testGetAllGroups() throws DAOException {
	service.getAllGroups();
	verify(dao, times(1)).getAllData();
    }

    @Test
    void saveTest() throws DAOException {
	var groups = Arrays.asList(new Group.GroupBuilder()
		.setName("Test")
		.build(),
		new Group.GroupBuilder()
			.setName("Test")
			.build());

	service.save(groups);
	verify(dao, times(2)).save(groups.get(0));
    }
}
