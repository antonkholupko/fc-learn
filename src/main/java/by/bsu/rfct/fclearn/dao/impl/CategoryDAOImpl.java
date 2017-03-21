package by.bsu.rfct.fclearn.dao.impl;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.entity.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {

    private static final Logger LOG = LogManager.getLogger(CategoryDAOImpl.class);

    private static final String PK_COLUMN = "id";
    private static final String QUERY_INSERT_CATEGORY = "INSERT INTO categories (name, image) " +
            "VALUES(?, ?);";
    private static final String QUERY_SELECT_CATEGORY = "SELECT id, name, image FROM categories " +
            "WHERE id=?;";
    private static final String QUERY_UPDATE_CATEGORY = "UPDATE categories SET name=?, image=? WHERE id=?;";
    private static final String QUERY_DELETE_CATEGORY = "DELETE FROM categories WHERE id=?;";
    private static final String QUERY_SELECT_ALL_CATEGORIES = "SELECT id, name, image FROM categories LIMIT ?,?;";
    private static final String QUERY_SELECT_CATEGORY_BY_NAME = "SELECT id, name, image FROM categories " +
            "WHERE name=?;";
    private static final String QUERY_INSERT_TOPIC_INTO_CATEGORY = "INSERT INTO topic_categories (topic_id, category_id) " +
            "VALUES(?,?);";
    private static final String QUERY_COUNT_ALL_CATEGORIES = "SELECT count(id) FROM categories;";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Category entity) {
        LOG.debug("CategoryDAO - create - name = {}", entity.getName());
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_CATEGORY, new String[]{PK_COLUMN});
            ps.setString(1, entity.getName());
            ps.setString(2,entity.getImage());
            return ps;
        }, holder);
        return holder.getKey().longValue();
    }

    @Override
    public Category read(Long id) {
        LOG.debug("CategoryDAO - read - id = {}", id);
        return jdbcTemplate.queryForObject(QUERY_SELECT_CATEGORY, new Object[]{id},
                new BeanPropertyRowMapper<>(Category.class));
    }

    @Override
    public Long update(Category entity) {
        LOG.debug("CategoryDAO - update - id = {}", entity.getId());
        jdbcTemplate.update(QUERY_UPDATE_CATEGORY, entity.getName(), entity.getImage(), entity.getId());
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        LOG.debug("CategoryDAO - delete - id = {}", id);
        jdbcTemplate.update(QUERY_DELETE_CATEGORY, id);
    }

    @Override
    public List<Category> readAll(Long startLimitFrom, Long amountOnPage) {
        LOG.debug("CategoryDAO - read all");
        return jdbcTemplate.query(QUERY_SELECT_ALL_CATEGORIES, new Object[]{startLimitFrom, amountOnPage},
                new BeanPropertyRowMapper<>(Category.class));
    }

    @Override
    public Boolean checkIfExist(Category entity) {
        LOG.debug("CategoryDAO - check if exists - name = {}", entity.getName());
        try {
            jdbcTemplate.queryForObject(QUERY_SELECT_CATEGORY_BY_NAME, new Object[]{entity.getName()},
                    new BeanPropertyRowMapper<>(Category.class));
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.debug("CategoryDAO - check if exists - name = {} doesn't exist", entity.getName());
            return false;
        }
    }

    @Override
    public Long countAll() {
        LOG.debug("CategoryDAO - count all");
        return jdbcTemplate.queryForObject(QUERY_COUNT_ALL_CATEGORIES, Long.class);
    }

    @Override
    public void addTopic(Long categoryId, Long topicId) {
        LOG.debug("CategoryDAO - add topic - id = {}", topicId);
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_TOPIC_INTO_CATEGORY);
            ps.setLong(1, topicId);
            ps.setLong(2, categoryId);
            return ps;
        }, holder);
    }
}
