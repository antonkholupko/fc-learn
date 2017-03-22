package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.config.DAOTestConfig;
import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.entity.Topic;
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
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = DAOTestConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = {"/topic/topic-dataset.xml"}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {"/topic/topic-dataset.xml"}, type = DatabaseOperation.DELETE)
public class TopicDAOTest {

    @Autowired
    private TopicDAO topicDAO;

    @Test
    public void create() throws Exception {
        Topic expectedTopic = new Topic();
        expectedTopic.setName("Collections");
        expectedTopic.setImage("http://google.com/images/array");
        Long id = topicDAO.create(expectedTopic);
        expectedTopic.setId(id);
        Topic topic = topicDAO.read(id);
        Assert.assertEquals(expectedTopic, topic);
    }

    @Test
    public void read() throws Exception {
        Long topicId = 4L;
        Topic expectedTopic = new Topic();
        expectedTopic.setId(topicId);
        expectedTopic.setName("CPCU");
        expectedTopic.setImage("http://dummyimage.com/194x131.jpg/cc0000/ffffff");
        Topic topic = topicDAO.read(topicId);
        Assert.assertEquals(expectedTopic, topic);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:topic/topic-update-dataset.xml")
    public void update() throws Exception {
        Long topicId = 11L;
        Topic topic = topicDAO.read(topicId);
        topic.setName("TestUpdateName");
        topic.setImage("http://test-update-image.com/244x194.jpg/cc0000/ffffff");
        Long updatedTopicId = topicDAO.update(topic);
        Topic updatedTopic = topicDAO.read(updatedTopicId);
        Assert.assertEquals(topic, updatedTopic);
    }

    @Test(expected = DataAccessException.class)
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:topic/topic-delete-dataset.xml")
    public void delete() throws Exception {
        Long topic = 10L;
        topicDAO.delete(topic);
        Topic test = topicDAO.read(topic);
        Assert.assertNull(test);
    }

    @Test
    public void readAll() throws Exception {
        int expectedTopicsAmount = 12;
        List<Topic> topics = topicDAO.readAll(2L, 12L);
        Assert.assertEquals(expectedTopicsAmount, topics.size());
    }

    @Test
    public void testCheckIfExist_topicExists() {
        String name = "Axure RP";
        Topic topic = new Topic();
        topic.setName(name);
        Boolean check = topicDAO.checkIfExist(topic);
        Assert.assertEquals(true, check);
    }

    @Test
    public void testCheckIfExist_topicNotExists() {
        String name = "Axure RP non exist";
        Topic topic = new Topic();
        topic.setName(name);
        Boolean check = topicDAO.checkIfExist(topic);
        Assert.assertEquals(false, check);
    }

    @Test
    public void testCountAll() {
        Long expectedTopicsAmount = 15L;
        Long topicsAmount = topicDAO.countAll();
        Assert.assertEquals(expectedTopicsAmount, topicsAmount);
    }

    @Test
    public void testCountCollectionAmount() {
        /*Long expectedCollectionAmount = 2L;
        Long collectionAmount = topicDAO.countCollectionAmount(10L);
        Assert.assertEquals(expectedCollectionAmount, collectionAmount);*/

    }

}