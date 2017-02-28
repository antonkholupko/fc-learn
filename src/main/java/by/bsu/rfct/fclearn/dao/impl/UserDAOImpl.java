package by.bsu.rfct.fclearn.dao.impl;

import by.bsu.rfct.fclearn.dao.UserDAO;
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

@Repository("userDAO")
public class UserDAOImpl implements UserDAO{

    private static final Logger LOG = LogManager.getLogger(UserDAOImpl.class.getName());

    private static final String PK_COLUMN = "id";
    private static final String QUERY_INSERT_USER = "INSERT INTO users (login, email, password, photo) " +
            "VALUES(?,?,?,?);";
    private static final String QUERY_UPDATE_USER = "UPDATE users SET login=?, email=?, password=?, photo=? " +
            "WHERE id=?;";
    private static final String QUERY_DELETE_USER = "DELETE FROM users WHERE id=?;";
    private static final String QUERY_SELECT_USER = "SELECT id, login, email, password, photo FROM users " +
            "WHERE id=?;";
    private static final String QUERY_SELECT_ALL_USERS = "SELECT id, login, email, password, photo " +
            "FROM users;";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(User entity) {
        LOG.debug("UserDAO - create - login = {}, email= {}", entity.getLogin(), entity.getEmail());
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_USER, new String[]{PK_COLUMN});
            ps.setString(1, entity.getLogin());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getPassword());
            ps.setString(4, entity.getPhoto());
            return ps;
        }, holder);
        return holder.getKey().longValue();
    }

    @Override
    public User read(Long id) {
        LOG.debug("UserDAO - read - id = {}", id);
        return jdbcTemplate.queryForObject(QUERY_SELECT_USER, new Object[]{id},
                new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public Long update(User entity) {
        LOG.debug("UserDAO - update - login = {}, email= {}", entity.getLogin(), entity.getEmail());
        jdbcTemplate.update(QUERY_UPDATE_USER, entity.getLogin(), entity.getEmail(),
                entity.getPassword(), entity.getPhoto(), entity.getId());
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        LOG.debug("UserDAO - delete - id = {}", id);
        jdbcTemplate.update(QUERY_DELETE_USER, id);
    }

    @Override
    public List<User> readAll() {
        LOG.debug("UserDAO - read all");
        return jdbcTemplate.query(QUERY_SELECT_ALL_USERS,
                new BeanPropertyRowMapper<>(User.class));
    }
}
