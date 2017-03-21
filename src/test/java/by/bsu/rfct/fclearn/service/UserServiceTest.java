package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dao.UserDAO;
import by.bsu.rfct.fclearn.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDAO userDAO;

    @Test
    public void create() throws Exception {

    }

    @Test
    public void read() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void readAll() throws Exception {

    }

    @Test
    public void countAll() throws Exception {
        Long expectedUsersAmount = 1005L;
        when(userDAO.countAll()).thenReturn(expectedUsersAmount);
        Long usersAmount = userService.countAll();
        verify(userDAO, times(1)).countAll();
        assertEquals(expectedUsersAmount, usersAmount);
    }

}