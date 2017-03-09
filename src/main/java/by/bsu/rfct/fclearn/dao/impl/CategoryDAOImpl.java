package by.bsu.rfct.fclearn.dao.impl;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.entity.User;
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
    private static final String QUERY_SELECT_ALL_CATEGOTIES = "SELECT id, name, image FROM categories;";

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
    public List<Category> readAll() {
        LOG.debug("CategoryDAO - read all");
        return jdbcTemplate.query(QUERY_SELECT_ALL_CATEGOTIES,
                new BeanPropertyRowMapper<>(Category.class));
    }
}
