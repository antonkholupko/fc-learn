package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.entity.Collection;

import java.util.List;

public interface CollectionDAO extends GenericDAO<Collection, Long>{

    List<Collection> readAllByTopicId(Long topicId, Long startLimitFrom, Long amountOnPage);

    Long countByAuthorId(Long authorId);

}
