package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.config.DAOTestConfig;
import by.bsu.rfct.fclearn.entity.Course;
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
@DatabaseSetup(value = {"/course/course-dataset.xml"}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {"/course/course-dataset.xml"}, type = DatabaseOperation.DELETE)
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
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:course/course-update-dataset.xml")
    public void testUpdate() {
        Long courseId = 9L;
        Course course = courseDAO.read(courseId);
        course.setCategoryId(1L);
        course.setName("TestTestCourse");
        course.setImage("http://dummyimage.com/169x147.jpg/ff4444/ffffff");
        Long updatedCourseId = courseDAO.update(course);
        Course updatedCourse = courseDAO.read(updatedCourseId);
        Assert.assertEquals(course, updatedCourse);
    }

    @Test(expected = DataAccessException.class)
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:course/course-delete-dataset.xml")
    public void testDelete() {
        Long courseId = 8L;
        courseDAO.delete(courseId);
        Course test = courseDAO.read(courseId);
        Assert.assertNull(test);
    }

    @Test
    public void testReadAll() {
        int expectedCoursesAmount = 20;
        List<Course> courses = courseDAO.readAll();
        Assert.assertEquals(expectedCoursesAmount, courses.size());
    }

}