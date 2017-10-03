package by.bsu.rfct.fclearn.dao.impl;

import by.bsu.rfct.fclearn.dao.UserDAO;
import by.bsu.rfct.fclearn.entity.Card;
import by.bsu.rfct.fclearn.entity.User;
import by.bsu.rfct.fclearn.service.exception.CannotLoginUserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.util.List;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

    private static final Logger LOG = LogManager.getLogger(UserDAOImpl.class);

    private static final String PK_COLUMN = "id";
    private static final String QUERY_INSERT_USER = "INSERT INTO users (login, email, password, photo, status) " +
            "VALUES(?,?,?,?,?);";
    private static final String QUERY_UPDATE_USER = "UPDATE users SET login=?, email=?, password=?, photo=?, status=? " +
            "WHERE id=?;";
    private static final String QUERY_DELETE_USER = "DELETE FROM users WHERE id=?;";
    private static final String QUERY_SELECT_USER = "SELECT id, login, email, password, photo, status AS statusString FROM users " +
            "WHERE id=?;";
    private static final String QUERY_SELECT_ALL_USERS = "SELECT id, login, email, password, photo, status AS statusString " +
            "FROM users LIMIT ?,?;";
    private static final String QUERY_SELECT_USER_BY_LOGIN = "SELECT id, login, email, password, photo, " +
            "status AS statusString FROM users WHERE login=?;";
    private static final String QUERY_SELECT_USER_BY_EMAIL = "SELECT id, login, email, password, photo, " +
            "status AS statusString FROM users WHERE email=?;";
    private static final String QUERY_INSERT_COLLECTION_INTO_USER = "INSERT INTO user_collections (user_id, " +
            "collection_id) VALUES(?,?);";
    private static final String QUERY_INSERT_CARD_INTO_USER = "INSERT INTO user_cards (users_id, cards_id, card_status, low_count) " +
            "VALUES(?,?,?,0);";
    private static final String QUERY_COUNT_ALL_USERS = "SELECT COUNT(id) FROM users;";
    private static final String QUERY_SELECT_BY_LOGIN_PASSWORD = "SELECT id, status AS statusString FROM users " +
            "WHERE login=? AND password=?;";
    private static final String QUERY_SELECT_BY_EMAIL_PASSWORD = "SELECT id, status AS statusString FROM users " +
            "WHERE email=? AND password=?;";
    private static final String QUERY_SELECT_BY_LOGIN_EMAIL_PASSWORD = "SELECT id, status AS statusString FROM users " +
            "WHERE login=? AND email=? AND password=?;";
    private static final String QUERY_SELECT_BY_LOGIN = "SELECT id, login, email, password, photo,status AS statusString " +
            "FROM users WHERE login=?;";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
            ps.setString(5, entity.getStatus().toString());
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
        LOG.debug("UserDAO - update - id = {}, email= {}", entity.getId(), entity.getEmail());
        jdbcTemplate.update(QUERY_UPDATE_USER, entity.getLogin(), entity.getEmail(),
                entity.getPassword(), entity.getPhoto(), entity.getStatus().toString(), entity.getId());
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        LOG.debug("UserDAO - delete - id = {}", id);
        jdbcTemplate.update(QUERY_DELETE_USER, id);
    }

    @Override
    public List<User> readAll(Integer startLimitFrom, Integer amountOnPage) {
        LOG.debug("UserDAO - read all");
        return jdbcTemplate.query(QUERY_SELECT_ALL_USERS, new Object[]{startLimitFrom, amountOnPage},
                new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public Boolean checkIfExist(User entity) {
        LOG.debug("UserDAO - check if exists");
        if (checkIfExistsByLogin(entity) || checkIfExistsByEmail(entity)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Long countAll() {
        LOG.debug("UserDAO - count all");
        return jdbcTemplate.queryForObject(QUERY_COUNT_ALL_USERS, Long.class);
    }

    @Override
    public void addCollection(Long userId, Long collectionId) {
        LOG.debug("UserDAO - add collection - id = {}", collectionId);
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_COLLECTION_INTO_USER);
            ps.setLong(1, userId);
            ps.setLong(2, collectionId);
            return ps;
        }, holder);
    }

    @Override
    public void addCard(Long userId, Long cardId) {
        LOG.debug("UserDAO - add card - id = {}", cardId);
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_CARD_INTO_USER);
            ps.setLong(1, userId);
            ps.setLong(2, cardId);
            ps.setString(3, Card.Status.NEW.toString());
            return ps;
        }, holder);
    }

    @Override
    public User readUserByLoginAndPassword(String login, String password) {
        LOG.debug("UserDAO - login user by login={}", login);
        try {
            return jdbcTemplate.queryForObject(QUERY_SELECT_BY_LOGIN_PASSWORD, new Object[]{login, password},
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException exc) {
            throw new CannotLoginUserException("No such user in database");
        }
    }

    @Override
    public User readUserByEmailAndPassword(String email, String password) {
        LOG.debug("UserDAO - login user by email={}", email);
        try {
            return jdbcTemplate.queryForObject(QUERY_SELECT_BY_EMAIL_PASSWORD, new Object[]{email, password},
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException exc) {
            throw new CannotLoginUserException("No such user in database");
        }
    }

    @Override
    public User readUserByLoginAndEmailAndPassword(String login, String email, String password) {
        LOG.debug("UserDAO - login user by login={}, email={}", login, email);
        try {
            return jdbcTemplate.queryForObject(QUERY_SELECT_BY_LOGIN_EMAIL_PASSWORD, new Object[]{login, email, password},
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException exc) {
            throw new CannotLoginUserException("No such user in database");
        }
    }

    @Override
    public User readByUsername(String login) {
        LOG.debug("UserDAO - read user by login");
        return jdbcTemplate.queryForObject(QUERY_SELECT_BY_LOGIN, new Object[]{login},
                new BeanPropertyRowMapper<>(User.class));
    }



    private Boolean checkIfExistsByLogin(User entity) {
        try {
            if (!StringUtils.isEmpty(entity.getLogin())) {
                LOG.debug("UserDAO - check if exists - login = {}", entity.getLogin());
                jdbcTemplate.queryForObject(QUERY_SELECT_USER_BY_LOGIN, new Object[]{entity.getLogin()},
                        new BeanPropertyRowMapper<>(User.class));
                return true;
            } else {
                return false;
            }
        } catch (EmptyResultDataAccessException exc) {
            LOG.debug("UserDAO - check if exists - login = {} doesn't exist", entity.getLogin());
            return false;
        }
    }

    private Boolean checkIfExistsByEmail(User entity) {
        try {
            if (!StringUtils.isEmpty(entity.getLogin())) {
                LOG.debug("UserDAO - check if exists - email = {}", entity.getEmail());
                jdbcTemplate.queryForObject(QUERY_SELECT_USER_BY_EMAIL, new Object[]{entity.getEmail()},
                        new BeanPropertyRowMapper<>(User.class));
                return true;
            } else {
                return false;
            }
        } catch (EmptyResultDataAccessException exc) {
            LOG.debug("UserDAO - check if exists - email = {} doesn't exist", entity.getEmail());
            return false;
        }
    }
}
