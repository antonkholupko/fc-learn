package by.bsu.rfct.fclearn.dao.impl;

import by.bsu.rfct.fclearn.dao.TopicDAO;
import by.bsu.rfct.fclearn.entity.Topic;
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

@Repository("topicDAO")
public class TopicDAOImpl implements TopicDAO{

    private static final Logger LOG = LogManager.getLogger(TopicDAOImpl.class);

    private static final String PK_COLUMN = "id";
    private static final String QUERY_INSERT_TOPIC = "INSERT INTO topics (name, image) " +
            "VALUES(?, ?);";
    private static final String QUERY_SELECT_TOPIC = "SELECT id, name, image FROM topics " +
            "WHERE id=?;";
    private static final String QUERY_UPDATE_TOPIC = "UPDATE topics SET name=?, image=? WHERE id=?;";
    private static final String QUERY_DELETE_TOPIC = "DELETE FROM topics WHERE id=?;";
    private static final String QUERY_SELECT_ALL_TOPICS = "SELECT id, name, image FROM topics LIMIT ?,?;";
    private static final String QUERY_SELECT_TOPIC_BY_NAME = "SELECT id, name, image FROM topics " +
            "WHERE name=?;";
    private static final String QUERY_COUNT_ALL_TOPICS = "SELECT count(id) FROM topics;";
    private static final String QUERY_COUNT_COLLECTIONS_IN_TOPIC = "SELECT count(topic_id) FROM collections " +
            "WHERE topic_id=?";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TopicDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Topic entity) {
        LOG.debug("TopicDAO - create - name = {}", entity.getName());
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_TOPIC, new String[]{PK_COLUMN});
            ps.setString(1, entity.getName());
            ps.setString(2,entity.getImage());
            return ps;
        }, holder);
        return holder.getKey().longValue();
    }

    @Override
    public Topic read(Long id) {
        LOG.debug("TopicDAO - read - id = {}", id);
        return jdbcTemplate.queryForObject(QUERY_SELECT_TOPIC, new Object[]{id},
                new BeanPropertyRowMapper<>(Topic.class));
    }

    @Override
    public Long update(Topic entity){
        LOG.debug("TopicDAO - update - id = {}", entity.getId());
        jdbcTemplate.update(QUERY_UPDATE_TOPIC, entity.getName(), entity.getImage(), entity.getId());
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        LOG.debug("TopicDAO - delete - id = {}", id);
        jdbcTemplate.update(QUERY_DELETE_TOPIC, id);
    }

    @Override
    public List<Topic> readAll(Long startLimitFrom, Long amountOnPage) {
        LOG.debug("TopicDAO - read all");
        return jdbcTemplate.query(QUERY_SELECT_ALL_TOPICS, new Object[]{startLimitFrom, amountOnPage},
                new BeanPropertyRowMapper<>(Topic.class));
    }

    @Override
    public Boolean checkIfExist(Topic entity) {
        LOG.debug("TopicDAO - check if exists - name = {}", entity.getName());
        try {
            jdbcTemplate.queryForObject(QUERY_SELECT_TOPIC_BY_NAME, new Object[]{entity.getName()},
                    new BeanPropertyRowMapper<>(Topic.class));
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.debug("TopicDAO - check if exists - name = {} doesn't exist", entity.getName());
            return false;
        }
    }

    @Override
    public Long countAll() {
        LOG.debug("TopicDAO - count all");
        return jdbcTemplate.queryForObject(QUERY_COUNT_ALL_TOPICS, Long.class);
    }

    @Override
    public Long countCollectionAmount(Long topicId) {
        LOG.debug("TopicDAO - count collections");
        return jdbcTemplate.queryForObject(QUERY_COUNT_COLLECTIONS_IN_TOPIC, new Object[] {topicId}, Long.class);
    }
}
