package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.config.DAOTestConfig;
import by.bsu.rfct.fclearn.entity.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DAOTestConfig.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = {"/user/user-dataset.xml"}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {"/user/user-dataset.xml"}, type = DatabaseOperation.DELETE)
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testCreate() {
        User expectedUser = new User();
        expectedUser.setLogin("Ron");
        expectedUser.setEmail("email@gmail.com");
        expectedUser.setPassword("a8ca4fd3eca649e721ccbabf0886f1f1");
        expectedUser.setPhoto("http://dummyimage.com/103x175.bmp/ff4444/ffffff");
        Long id = userDAO.create(expectedUser);
        expectedUser.setId(id);
        User user = userDAO.read(id);
        Assert.assertEquals(expectedUser, user);
    }

    @Test
    public void testRead() {
        Long userID = 13L;
        User expectedUser = new User();
        expectedUser.setId(userID);
        expectedUser.setLogin("David");
        expectedUser.setPassword("66e930b1e7a57dd0ba2bd6939c330486");
        expectedUser.setEmail("dadamsc@people.com.cn");
        expectedUser.setPhoto("http://dummyimage.com/176x138.jpg/cc0000/ffffff");
        User user = userDAO.read(userID);
        Assert.assertEquals(expectedUser, user);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:user/user-update-dataset.xml")
    public void testUpdate() {
        Long userId = 15L;
        User user = userDAO.read(userId);
        user.setLogin("Anton");
        user.setEmail("emailaa@gmail.com");
        user.setPassword("a8ca4fd3eca649e725ccbabf0886f1f1");
        user.setPhoto("http://dummyimage.com/144x175.bmp/ff4444/ffffff");
        Long updatedUserId = userDAO.update(user);
        User updatedUser = userDAO.read(updatedUserId);
        Assert.assertEquals(user, updatedUser);
    }

    @Test(expected = DataAccessException.class)
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:user/user-delete-dataset.xml")
    public void testDelete() {
        Long userID = 18L;
        userDAO.delete(userID);
        User test = userDAO.read(userID);
        Assert.assertNull(test);
    }

    @Test
    public void testReadAll() {
        int expectedUsersAmount = 20;
        List<User> users = userDAO.readAll();
        Assert.assertEquals(expectedUsersAmount, users.size());
    }

}