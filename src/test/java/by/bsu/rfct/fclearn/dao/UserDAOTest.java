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
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
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
        expectedUser.setStatus(User.Status.ADMIN);
        Long id = userDAO.create(expectedUser);
        expectedUser.setId(id);
        User user = userDAO.read(id);
        Assert.assertEquals(expectedUser, user);
    }

    @Test
    public void testRead() {
        Long userID = 9L;
        User expectedUser = new User();
        expectedUser.setId(userID);
        expectedUser.setLogin("abishop8");
        expectedUser.setPassword("6a22a16ae01c131072037a5e44e42790");
        expectedUser.setEmail("fhill8@disqus.com");
        expectedUser.setPhoto("http://dummyimage.com/101x168.jpg/ff4444/ffffff");
        expectedUser.setStatus(User.Status.ACTIVE);
        User user = userDAO.read(userID);
        Assert.assertEquals(expectedUser, user);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:user/user-update-dataset.xml")
    public void testUpdate() {
        Long userId = 27L;
        User user = userDAO.read(userId);
        user.setLogin("TestUpdateLogin");
        user.setEmail("test.update.login@gmail.com");
        user.setPassword("2d14e1d919c753e2823796653b658f86");
        user.setPhoto("http://dummyimage.com/123x215.png/ff4444/ffffff");
        user.setStatus(User.Status.ACTIVE);
        Long updatedUserId = userDAO.update(user);
        User updatedUser = userDAO.read(updatedUserId);
        Assert.assertEquals(user, updatedUser);
    }

    @Test(expected = DataAccessException.class)
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:user/user-delete-dataset.xml")
    public void testDelete() {
        Long userID = 16L;
        userDAO.delete(userID);
        User test = userDAO.read(userID);
        Assert.assertNull(test);
    }

    @Test
    public void testReadAll() {
        int expectedUsersAmount = 30;
        List<User> users = userDAO.readAll();
        Assert.assertEquals(expectedUsersAmount, users.size());
    }

    @Test
    public void testCheckIfExist_userExists() {
        String login = "ntuckerm";
        String email = "ltorresm@opera.com";
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin(login);
        user2.setEmail(email);
        user3.setLogin(login);
        user3.setEmail(email);
        Boolean check1 = userDAO.checkIfExist(user1);
        Boolean check2 = userDAO.checkIfExist(user2);
        Boolean check3 = userDAO.checkIfExist(user3);
        Assert.assertEquals(true, check1);
        Assert.assertEquals(true, check2);
        Assert.assertEquals(true, check3);
    }

    @Test
    public void testCheckIfExist_userNotExists() {
        String login = "Anton";
        String email = "aaaa@gmail.com";
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        Boolean check = userDAO.checkIfExist(user);
        Assert.assertEquals(false, check);
    }

    @Test
    public void testAddCollection() {
        Long userId = 10L;
        Long collectionId = 15L;
        userDAO.addCollection(userId, collectionId);
    }

    @Test
    public void testAddCard() {
        Long userId = 20L;
        Long cardId = 2L;
        userDAO.addCard(userId, cardId);
    }

    @Test
    public void testCountAll() {
        Long expectedUsersAmount = 30L;
        Long usersAmount = userDAO.countAll();
        Assert.assertEquals(expectedUsersAmount, usersAmount);
    }

}