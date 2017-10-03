package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CardDAO;
import by.bsu.rfct.fclearn.dto.card.CardConverter;
import by.bsu.rfct.fclearn.dto.card.CardDTO;
import by.bsu.rfct.fclearn.entity.Card;
import by.bsu.rfct.fclearn.entity.CardStatus;
import by.bsu.rfct.fclearn.service.TrainingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Service("trainingService")
public class TrainingServiceImpl implements TrainingService {

    private static final Logger LOG = LogManager.getLogger(TopicServiceImpl.class);

    private CardDAO cardDAO;
    private CardConverter cardConverter;
    private Map<Long, Map<Long, Queue<CardStatus>>> userCollectionBasketMap;
    private ObjectFactory<Queue<CardStatus>> cardStatusQueueFactory;

    public TrainingServiceImpl(CardDAO cardDAO, CardConverter cardConverter,
                               Map<Long, Map<Long, Queue<CardStatus>>> userCollectionBasketMap,
                               ObjectFactory<Queue<CardStatus>> cardStatusQueueFactory) {
        this.cardDAO = cardDAO;
        this.cardConverter = cardConverter;
        this.userCollectionBasketMap = userCollectionBasketMap;
        this.cardStatusQueueFactory = cardStatusQueueFactory;
    }

    @Override
    public CardDTO getNextCard(Long userId, Long collectionId) {
        startTraining(userId, collectionId);

        Queue<CardStatus> cardStatusQueue = userCollectionBasketMap.get(userId).get(collectionId);

        Card card;
        do {
            CardStatus cardStatus = cardStatusQueue.poll();
            cardStatusQueue.add(cardStatus);
            card = cardDAO.getNextCardForUserTraining(userId, collectionId, cardStatus);
        } while (card == null);

        return cardConverter.convert(card);
    }

    @Override
    public void knownCard(Long userId, Long cardId) {
        cardDAO.knownCard(userId, cardId);
    }

    @Override
    public void unknownCard(Long userId, Long cardId) {
        cardDAO.unknownCard(userId, cardId);
    }

    private void startTraining(Long userId, Long collectionId) {
        userCollectionBasketMap.putIfAbsent(userId, new HashMap<>());
        Map<Long, Queue<CardStatus>> collectionBasketMap = userCollectionBasketMap.get(userId);
        collectionBasketMap.putIfAbsent(collectionId, cardStatusQueueFactory.getObject());
    }
}

