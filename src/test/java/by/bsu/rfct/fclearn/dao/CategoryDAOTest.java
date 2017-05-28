package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.config.DAOTestConfig;
import by.bsu.rfct.fclearn.entity.Category;
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
@DatabaseSetup(value = {"/category/category-dataset.xml"}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {"/category/category-dataset.xml"}, type = DatabaseOperation.DELETE)
public class CategoryDAOTest {

    @Autowired
    private CategoryDAO categoryDAO;

    @Test
    public void testCreate() {
        Category expectedCategory = new Category();
        expectedCategory.setName("Java");
        expectedCategory.setImage("http://google.com/images/first");
        Long id = categoryDAO.create(expectedCategory);
        expectedCategory.setId(id);
        Category category = categoryDAO.read(id);
        Assert.assertEquals(expectedCategory, category);
    }

    @Test
    public void testRead() {
        Long categoryId = 2L;
        Category expectedCategory = new Category();
        expectedCategory.setId(categoryId);
        expectedCategory.setName("Balanced");
        expectedCategory.setImage("http://dummyimage.com/136x111.jpg/dddddd/000000");
        Category category = categoryDAO.read(categoryId);
        Assert.assertEquals(expectedCategory, category);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:category/category-update-dataset.xml")
    public void testUpdate() {
        Long categoryId = 5L;
        Category category = categoryDAO.read(categoryId);
        category.setName("TestTest");
        category.setImage("TestImage");
        Long updatedCategoryId = categoryDAO.update(category);
        Category updatedCategory = categoryDAO.read(updatedCategoryId);
        Assert.assertEquals(category, updatedCategory);
    }

    @Test(expected = DataAccessException.class)
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:category/category-delete-dataset.xml")
    public void testDelete() {
        Long categoryId = 4L;
        categoryDAO.delete(categoryId);
        Category test = categoryDAO.read(categoryId);
        Assert.assertNull(test);
    }

    @Test
    public void testReadAll() {
        int expectedCategoriesAmount = 3;
        List<Category> categories = categoryDAO.readAll(2, 3);
        Assert.assertEquals(expectedCategoriesAmount, categories.size());
    }

    @Test
    public void testCheckIfExist_categoryExists() {
        String name = "structure";
        Category category = new Category();
        category.setName(name);
        Boolean check = categoryDAO.checkIfExist(category);
        Assert.assertEquals(true, check);
    }

    @Test
    public void testCheckIfExist_categoryNotExists() {
        String name = "noSuchCategory";
        Category category = new Category();
        category.setName(name);
        Boolean check = categoryDAO.checkIfExist(category);
        Assert.assertEquals(false, check);
    }

    @Test
    public void testAddTopic() {
        categoryDAO.addTopic(4L, 1L);
    }

    @Test
    public void testCountAll() {
        Long expectedCategoriesAmount = 8L;
        Long categoriesAmount = categoryDAO.countAll();
        Assert.assertEquals(expectedCategoriesAmount, categoriesAmount);
    }

    @Test
    public void testCountTopicAmount() {
        Long expectedTopicAmount = 3L;
        Long topicAmount = categoryDAO.countTopicAmount(3L);
        Assert.assertEquals(expectedTopicAmount, topicAmount);
    }

}