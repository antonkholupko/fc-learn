package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.entity.Topic;

public interface TopicDAO extends GenericDAO<Topic, Long>{

    /**
     * Counts collection amount in topic
     * @param topicId topic id
     * @return collection amount in topic
     */
    Long countCollectionAmount(Long topicId);

}
