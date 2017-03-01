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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
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
        expectedCategory.setName("congue");
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
        int expectedCategoriesAmount = 7;
        List<Category> categories = categoryDAO.readAll();
        Assert.assertEquals(expectedCategoriesAmount, categories.size());
    }

}