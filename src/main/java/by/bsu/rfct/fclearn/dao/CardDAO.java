package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.entity.Card;

public interface CardDAO extends GenericDAO<Card, Long>{

    Long countCardAmountInCollection(Long collectionId);

}
