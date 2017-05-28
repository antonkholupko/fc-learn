package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.entity.User;

public interface UserDAO extends GenericDAO<User, Long> {

    void addCollection(Long userId, Long collectionId);

    void addCard(Long userId, Long cardId);

    User readUserByLoginAndPassword(String login, String password);

    User readUserByEmailAndPassword(String email, String password);

    User readUserByLoginAndEmailAndPassword(String login, String email, String password);

    User readByUsername(String username);
}
