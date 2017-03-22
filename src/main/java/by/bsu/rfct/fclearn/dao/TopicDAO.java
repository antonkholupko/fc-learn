package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.entity.Topic;

import java.util.List;

public interface TopicDAO extends GenericDAO<Topic, Long>{

    /**
     * Counts collection amount in topic
     * @param topicId topic id
     * @return collection amount in topic
     */
    Long countCollectionAmount(Long topicId);
}
