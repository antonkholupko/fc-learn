package by.bsu.rfct.fclearn.dao.impl;

import by.bsu.rfct.fclearn.dao.CardDAO;
import by.bsu.rfct.fclearn.entity.Card;
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

@Repository("cardDAO")
public class CardDAOImpl implements CardDAO{

    private static final Logger LOG = LogManager.getLogger(CardDAOImpl.class);

    private static final String PK_COLUMN = "id";
    private static final String QUERY_INSERT_CARD = "INSERT INTO cards (collection_id, question, " +
            "answer, question_image, answer_image) VALUES(?, ?, ?, ?, ?);";
    private static final String QUERY_SELECT_CARD = "SELECT id, collection_id, question, answer, " +
            "question_image, answer_image FROM cards WHERE id=?;";
    private static final String QUERY_UPDATE_CARD = "UPDATE cards SET collection_id=?, question=?, answer=?, " +
            "question_image=?, answer_image=? WHERE id=?;";
    private static final String QUERY_DELETE_CARD = "DELETE FROM cards WHERE id=?;";
    private static final String QUERY_SELECT_ALL_CARDS = "SELECT id, collection_id, question, answer, " +
            "question_image, answer_image FROM cards;";
    private static final String QUERY_SELECT_CARD_BY_QUESTION = "SELECT id, collection_id, question, answer, " +
            "question_image, answer_image FROM cards WHERE question=?;";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CardDAOImpl(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Card entity) {
        LOG.debug("CardDAO - create - card");
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_CARD, new String[]{PK_COLUMN});
            ps.setLong(1, entity.getCollectionId());
            if (entity.getQuestion() != null) {
                ps.setString(2, entity.getQuestion());
            }
            if (entity.getAnswer() != null) {
                ps.setString(3, entity.getAnswer());
            }
            if (entity.getQuestionImage() != null) {
                ps.setString(4, entity.getQuestionImage());
            }
            if (entity.getAnswerImage() != null) {
                ps.setString(5, entity.getAnswerImage());
            }
            return ps;
        }, holder);
        return holder.getKey().longValue();
    }

    @Override
    public Card read(Long id) {
        LOG.debug("CardDAO - read - id = {}", id);
        return jdbcTemplate.queryForObject(QUERY_SELECT_CARD, new Object[]{id},
                new BeanPropertyRowMapper<>(Card.class));
    }

    @Override
    public Long update(Card entity) {
        LOG.debug("CardDAO - update - id = {}", entity.getId());
        jdbcTemplate.update(QUERY_UPDATE_CARD, entity.getCollectionId(), entity.getQuestion(), entity.getAnswer(),
                entity.getQuestionImage(), entity.getAnswerImage(), entity.getId());
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        LOG.debug("CardDAO - delete - id = {}", id);
        jdbcTemplate.update(QUERY_DELETE_CARD, id);
    }

    @Override
    public List<Card> readAll() {
        LOG.debug("CardDAO - read all");
        return jdbcTemplate.query(QUERY_SELECT_ALL_CARDS,
                new BeanPropertyRowMapper<>(Card.class));
    }

    @Override
    public Boolean checkIfExist(Card entity) {
        LOG.debug("CardDAO - check if exists - question = {}", entity.getQuestion());
        try {
            jdbcTemplate.queryForObject(QUERY_SELECT_CARD_BY_QUESTION, new Object[]{entity.getQuestion()},
                    new BeanPropertyRowMapper<>(Card.class));
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.debug("CardDAO - check if exists - question = {} doesn't exist", entity.getQuestion());
            return false;
        }
    }
}