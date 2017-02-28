package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.config.DAOTestConfig;
import by.bsu.rfct.fclearn.entity.Category;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DAOTestConfig.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = {"/category/category-dataset.xml"}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {"/category/category-dataset.xml"}, type = DatabaseOperation.DELETE)
public class CategoryDAOTest {

    @Autowired
    CategoryDAO categoryDAO;

    @Test
    public void create() {
        Category expectedCategory = new Category();
        expectedCategory.setName("Java");
        Long id = categoryDAO.create(expectedCategory);
        expectedCategory.setId(id);
        Category category = categoryDAO.read(id);
        Assert.assertEquals(expectedCategory, category);
    }

    @Test
    public void read() {
        Long categoryId = 2L;
        Category expectedCategory = new Category();
        expectedCategory.setId(categoryId);
        expectedCategory.setName("congue");
        Category category = categoryDAO.read(categoryId);
        Assert.assertEquals(expectedCategory, category);
    }

    @Test
    public void update() {

    }

    @Test
    public void delete() {

    }

    @Test
    public void readAll() {

    }

}