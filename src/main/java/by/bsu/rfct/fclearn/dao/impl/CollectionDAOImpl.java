package by.bsu.rfct.fclearn.dao.impl;

import by.bsu.rfct.fclearn.dao.CollectionDAO;
import by.bsu.rfct.fclearn.entity.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

@Repository("collectionDAO")
public class CollectionDAOImpl implements CollectionDAO {

    private static final Logger LOG = LogManager.getLogger(CollectionDAOImpl.class);

    private static final String PK_COLUMN = "id";
    private static final String QUERY_INSERT_COLLECTION = "INSERT INTO collections (author_id, topic_id, name, " +
            "description, created, modified, image, status, rating) VALUES (?,?,?,?,?,?,?,?,?);";
    private static final String QUERY_SELECT_COLLECTION = "SELECT id, author_id, topic_id, name, description, " +
            "created, modified, image, status AS statusString, rating FROM collections WHERE id=?;";
    private static final String QUERY_UPDATE_COLLECTION = "UPDATE collections SET author_id=?, topic_id=?, name=?, " +
            "description=?, created=?, modified=?, image=?, status=?, rating=? WHERE id=?";
    private static final String QUERY_DELETE_COLLECTION = "DELETE FROM collections WHERE id=?;";
    private static final String QUERY_SELECT_ALL_COLLECTIONS = "SELECT id, author_id, topic_id, name, description, " +
            "created, modified, image, status AS statusString, rating FROM collections LIMIT ?,?;";
    private static final String QUERY_SELECT_COLLECTION_BY_NAME = "SELECT id, author_id, topic_id, name, description, " +
            "created, modified, image, status AS statusString, rating FROM collections WHERE name=?;";
    private static final String QUERY_COUNT_ALL_COLLECTIONS = "SELECT count(id) FROM collections;";
    private static final String QUERY_SELECT_ALL_BY_TOPIC_ID = "SELECT id, author_id, topic_id, name, description, " +
            "created, modified, image, status AS statusString, rating FROM collections WHERE topic_id=? LIMIT ?,?;";
    private static final String QUERY_COUNT_BY_AUTHOR_ID = "SELECT count(id) FROM collections WHERE author_id=?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CollectionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(Collection entity) {
        LOG.debug("CollectionDAO - create - name = {}", entity.getName());
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_COLLECTION, new String[]{PK_COLUMN});
            ps.setLong(1, entity.getAuthorId());
            ps.setLong(2, entity.getTopicId());
            ps.setString(3, entity.getName());
            ps.setString(4, entity.getDescription());
            if (entity.getCreated() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(entity.getCreated()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }
            if (entity.getModified() != null) {
                ps.setTimestamp(6, Timestamp.valueOf(entity.getModified()));
            } else {
                ps.setNull(6, Types.TIMESTAMP);
            }
            ps.setString(7, entity.getImage());
            ps.setString(8, entity.getStatus().toString());
            ps.setInt(9, entity.getRating());
            return ps;
        }, holder);
        return holder.getKey().longValue();
    }

    @Override
    public Collection read(Long id) {
        LOG.debug("CollectionDAO - read - id = {}", id);
        return jdbcTemplate.queryForObject(QUERY_SELECT_COLLECTION, new Object[]{id},
                new BeanPropertyRowMapper<>(Collection.class));
    }

    @Override
    public Long update(Collection entity) {
        LOG.debug("CollectionDAO - update - id = {}, name= {}", entity.getId(), entity.getName());
        jdbcTemplate.update(QUERY_UPDATE_COLLECTION, entity.getAuthorId(), entity.getTopicId(), entity.getName(),
                entity.getDescription(), Timestamp.valueOf(entity.getCreated()), Timestamp.valueOf(entity.getModified()),
                entity.getImage(), entity.getStatus().toString(), entity.getRating(), entity.getId());
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        LOG.debug("CollectionDAO - delete - id = {}", id);
        jdbcTemplate.update(QUERY_DELETE_COLLECTION, id);
    }

    @Override
    public List<Collection> readAll(Integer startLimitFrom, Integer amountOnPage) {
        LOG.debug("CollectionDAO - read all");
        return jdbcTemplate.query(QUERY_SELECT_ALL_COLLECTIONS, new Object[]{startLimitFrom, amountOnPage},
                new BeanPropertyRowMapper<>(Collection.class));
    }

    @Override
    public Boolean checkIfExist(Collection entity) {
        LOG.debug("CollectionDAO - check if exists - name = {}", entity.getName());
        try {
            jdbcTemplate.queryForObject(QUERY_SELECT_COLLECTION_BY_NAME, new Object[]{entity.getName()},
                    new BeanPropertyRowMapper<>(Collection.class));
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.debug("CollectionDAO - check if exists - name = {} doesn't exist", entity.getName());
            return false;
        }
    }

    @Override
    public Long countAll() {
        LOG.debug("CollectionDAO - count all");
        return jdbcTemplate.queryForObject(QUERY_COUNT_ALL_COLLECTIONS, Long.class);
    }

    @Override
    public List<Collection> readAllByTopicId(Long topicId, Integer startLimitFrom, Integer amountOnPage) {
        LOG.debug("CollectionDAO - read all by topic id={}", topicId);
        return jdbcTemplate.query(QUERY_SELECT_ALL_BY_TOPIC_ID, new Object[]{topicId, startLimitFrom, amountOnPage},
                new BeanPropertyRowMapper<>(Collection.class));
    }

    @Override
    public Long countByAuthorId(Long authorId) {
        LOG.debug("CollectionDAO - count by author id");
        return jdbcTemplate.queryForObject(QUERY_COUNT_BY_AUTHOR_ID, new Object[]{authorId}, Long.class);
    }
}
