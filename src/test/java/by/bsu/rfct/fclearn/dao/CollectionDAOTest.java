package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.config.DAOTestConfig;
import by.bsu.rfct.fclearn.entity.Collection;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DAOTestConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = {"/collection/collection-dataset.xml"}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {"/collection/collection-dataset.xml"}, type = DatabaseOperation.DELETE)
public class CollectionDAOTest {

    @Autowired
    private CollectionDAO collectionDAO;

    @Test
    public void create() throws Exception {
        Collection expectedCollection = new Collection();
        expectedCollection.setAuthorId(3L);
        expectedCollection.setTopicId(5L);
        expectedCollection.setName("First collection");
        expectedCollection.setDescription("Description of collection");
        expectedCollection.setCreated(LocalDateTime.now());
        expectedCollection.setModified(LocalDateTime.now());
        expectedCollection.setImage("http://dummyimage.com/103x175.bmp/ff4444/ffffff");
        expectedCollection.setStatus(Collection.Status.PRIVATE);
        expectedCollection.setRating(0);
        Long id = collectionDAO.create(expectedCollection);
        expectedCollection.setId(id);
        Collection collection = collectionDAO.read(id);
        Assert.assertEquals(expectedCollection, collection);
    }

    @Test
    public void read() throws Exception {
        Long collectionId = 17L;
        Collection expectedCollection = new Collection();
        expectedCollection.setId(collectionId);
        expectedCollection.setAuthorId(17L);
        expectedCollection.setTopicId(13L);
        expectedCollection.setName("Quaxo");
        expectedCollection.setDescription("Pellentesque at nulla. Suspendisse potenti.");
        expectedCollection.setCreated(LocalDateTime.of(2016, 6, 21, 0, 0, 0));
        expectedCollection.setModified(LocalDateTime.of(2016, 10, 31, 0, 0, 0));
        expectedCollection.setImage("http://dummyimage.com/172x102.png/ff4444/ffffff");
        expectedCollection.setStatus(Collection.Status.PRIVATE);
        expectedCollection.setRating(17);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:collection/collection-update-dataset.xml")
    public void update() throws Exception {
        Long collectionId = 10L;
        Collection collection = collectionDAO.read(collectionId);
        collection.setAuthorId(10L);
        collection.setTopicId(10L);
        collection.setName("TestUpdateName");
        collection.setDescription("Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.");
        collection.setCreated(LocalDateTime.of(2016, 9, 6, 0, 0, 0));
        collection.setModified(LocalDateTime.of(2016, 9, 22, 0, 0, 0));
        collection.setImage("http://dummyimage.com/117x152.png/5fa2dd/ffffff");
        collection.setStatus(Collection.Status.REQ);
        collection.setRating(11);
        Long updatedCollectionId = collectionDAO.update(collection);
        Collection updatedCollection = collectionDAO.read(updatedCollectionId);
        Assert.assertEquals(collection, updatedCollection);
    }

    @Test(expected = DataAccessException.class)
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:collection/collection-delete-dataset.xml")
    public void delete() throws Exception {
        Long collectionId = 20L;
        collectionDAO.delete(collectionId);
        Collection test = collectionDAO.read(collectionId);
        Assert.assertNull(test);
    }

    @Test
    public void readAll() throws Exception {
        int expectedColletionsAmount = 20;
        List<Collection> collections = collectionDAO.readAll();
        Assert.assertEquals(expectedColletionsAmount, collections.size());
    }

    @Test
    public void testCheckIfExist_collectionExists() {
        String name = "Dabfeed";
        Collection collection = new Collection();
        collection.setName(name);
        Boolean check = collectionDAO.checkIfExist(collection);
        Assert.assertEquals(true, check);
    }

    @Test
    public void testCheckIfExist_collectionNotExists() {
        String name = "DabfeedNotexistedTest";
        Collection collection = new Collection();
        collection.setName(name);
        Boolean check = collectionDAO.checkIfExist(collection);
        Assert.assertEquals(false, check);
    }

    @Test
    public void testCountAll() {
        Long expectedCollectionsAmount = 20L;
        Long collectionsAmount = collectionDAO.countAll();
        Assert.assertEquals(expectedCollectionsAmount, collectionsAmount);
    }

}