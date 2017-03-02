package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.config.DAOTestConfig;
import by.bsu.rfct.fclearn.entity.Course;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DAOTestConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = {"/course/course_dataset.xml"}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {"/course/course_dataset.xml"}, type = DatabaseOperation.DELETE)
public class CourseDAOTest {

    @Autowired
    private CourseDAO courseDAO;

    @Test
    public void testCreate() {
        Course expectedCourse = new Course();
        expectedCourse.setCategoryId(1L);
        expectedCourse.setName("Java");
        expectedCourse.setImage("http//someimage.com");
        Long id = courseDAO.create(expectedCourse);
        expectedCourse.setId(id);
        Course course = courseDAO.read(id);
        Assert.assertEquals(expectedCourse, course);
    }

    @Test
    public void testRead() {
        Long courseId = 3L;
        Course expectedCourse = new Course();
        expectedCourse.setId(courseId);
        expectedCourse.setCategoryId(3L);
        expectedCourse.setName("Multi-channelled");
        expectedCourse.setImage("http://dummyimage.com/159x228.bmp/5fa2dd/ffffff");
        Course course = courseDAO.read(courseId);
        Assert.assertEquals(expectedCourse, course);
    }

    @Test
    public void testUpdate() {

    }

    @Test
    public void testDelete() {

    }

    @Test
    public void testReadAll() {

    }

}