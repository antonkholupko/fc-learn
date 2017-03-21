package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.entity.User;

public interface UserDAO extends GenericDAO<User, Long>{

    void addCollection(Long userId, Long collectionId);

    void addCard(Long userId, Long cardId);

}
