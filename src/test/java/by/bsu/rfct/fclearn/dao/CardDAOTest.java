package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.config.DAOTestConfig;
import by.bsu.rfct.fclearn.entity.Card;
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
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = DAOTestConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = {"/card/card-dataset.xml"}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {"/card/card-dataset.xml"}, type = DatabaseOperation.DELETE)
public class CardDAOTest {

    @Autowired
    private CardDAO cardDAO;

    @Test
    public void create() throws Exception {
        Card expectedCard = new Card();
        expectedCard.setCollectionId(2L);
        expectedCard.setQuestion("Question test card?");
        expectedCard.setAnswer("Answer for test card.");
        expectedCard.setQuestionImage("http://google.com/question_img");
        expectedCard.setAnswerImage("http://google.com/answer_img");
        Long id = cardDAO.create(expectedCard);
        expectedCard.setId(id);
        Card card = cardDAO.read(id);
        Assert.assertEquals(expectedCard, card);
    }

    @Test
    public void read() throws Exception {
        Long cardId = 11L;
        Card expectedCard = new Card();
        expectedCard.setId(cardId);
        expectedCard.setCollectionId(11L);
        expectedCard.setQuestion("Maecenas pulvinar lobortis est. Phasellus sit amet erat. Nulla tempus.");
        expectedCard.setAnswer("Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla.");
        expectedCard.setQuestionImage("http://dummyimage.com/135x184.png/dddddd/000000");
        expectedCard.setAnswerImage("http://dummyimage.com/227x104.png/dddddd/000000");
        Card card = cardDAO.read(cardId);
        Assert.assertEquals(expectedCard, card);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:card/card-update-dataset.xml")
    public void update() throws Exception {
        Long cardId = 59L;
        Card card = cardDAO.read(cardId);
        card.setCollectionId(10L);
        card.setAnswer("Morbi non lectus test.");
        card.setQuestionImage("http://dummyimagetest.com/127x114.png/ff4444/ffffff");
        Long updatedCardId = cardDAO.update(card);
        Card updatedCard = cardDAO.read(updatedCardId);
        Assert.assertEquals(card, updatedCard);
    }

    @Test(expected = DataAccessException.class)
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "classpath:card/card-delete-dataset.xml")
    public void delete() throws Exception {
        Long cardId = 47L;
        cardDAO.delete(cardId);
        Card test = cardDAO.read(cardId);
        Assert.assertNull(test);
    }

    @Test
    public void readAll() throws Exception {
        int expectedCardsAmount = 12;
        Long expectedId = 19L;
        List<Card> cards = cardDAO.readAll(18, 12);
        Long id = cards.get(0).getId();
        Assert.assertEquals(expectedId, id);
        Assert.assertEquals(expectedCardsAmount, cards.size());
    }

    @Test
    public void checkIfExist_cardExists() {
        String question = "In sagittis dui vel nisl.";
        Card card = new Card();
        card.setQuestion(question);
        Boolean check = cardDAO.checkIfExist(card);
        Assert.assertEquals(true, check);
    }

    @Test
    public void checkIfExists_cardNotExists() {
        String question = "Test question for not existing card";
        Card card = new Card();
        card.setQuestion(question);
        Boolean check = cardDAO.checkIfExist(card);
        Assert.assertEquals(false, check);
    }

    @Test
    public void testCountAll() {
        Long expectedCardsAmount = 60L;
        Long cardAmount = cardDAO.countAll();
        Assert.assertEquals(expectedCardsAmount, cardAmount);
    }

}