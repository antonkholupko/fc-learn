package by.bsu.rfct.fclearn.dao.impl;

import by.bsu.rfct.fclearn.dao.CourseDAO;
import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.entity.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository("courseDAO")
public class CourseDAOImpl implements CourseDAO{

    private static final Logger LOG = LogManager.getLogger(CourseDAOImpl.class);

    private static final String PK_COLUMN = "id";
    private static final String QUERY_INSERT_COURSE = "INSERT INTO courses (category_id, name, image) " +
            "VALUES (?,?,?);";
    private static final String QUERY_SELECT_COURSE = "SELECT id, category_id, name, image FROM courses " +
            "WHERE id=?;";
    private static final String QUERY_UPDATE_COURSE = "UPDATE courses SET category_id=?, name=?, image=? " +
            "WHERE id=?;";
    private static final String QUERY_DELETE_COURSE = "DELETE FROM courses WHERE id=?;";
    private static final String QUERY_SELECT_ALL_COURSES = "SELECT id, category_id, name, image FROM courses;";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Course entity) {
        LOG.debug("CourseDAO - create - name = {}", entity.getName());
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_COURSE, new String[]{PK_COLUMN});
            ps.setLong(1, entity.getCategoryId());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getImage());
            return ps;
        }, holder);
        return holder.getKey().longValue();
    }

    @Override
    public Course read(Long id) {
        LOG.debug("CourseDAO - read - id = {}", id);
        return jdbcTemplate.queryForObject(QUERY_SELECT_COURSE, new Object[]{id},
                new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public Long update(Course entity) {
        LOG.debug("CourseDAO - update - id = {}", entity.getId());
        jdbcTemplate.update(QUERY_UPDATE_COURSE, entity.getCategoryId(), entity.getName(),
                entity.getImage(), entity.getId());
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        LOG.debug("CourseDAO - delete - id = {}", id);
        jdbcTemplate.update(QUERY_DELETE_COURSE, id);
    }

    @Override
    public List<Course> readAll() {
        LOG.debug("CourseDAO - read all");
        return jdbcTemplate.query(QUERY_SELECT_ALL_COURSES,
                new BeanPropertyRowMapper<>(Course.class));
    }
}
