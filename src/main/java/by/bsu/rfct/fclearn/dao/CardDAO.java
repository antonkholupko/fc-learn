package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.entity.Card;

import java.util.List;

public interface CardDAO extends GenericDAO<Card, Long> {

    Long countCardAmountInCollection(Long collectionId);

    List<Card> readAllCardsByCollectionId(Long collectionId, Integer startLimitFrom, Integer amountOnPage);

}
