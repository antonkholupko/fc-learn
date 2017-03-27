package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.entity.Topic;

import java.util.List;

public interface TopicDAO extends GenericDAO<Topic, Long>{

    /**
     * Counts collection amount in topic
     * @param topicId topic id
     * @return collection amount in topic
     */
    Long countCollectionAmount(Long topicId);

    /**
     * Reads topics by category id
     * @param categoryId category id
     * @return list of topics
     */
    List<Topic> readAllByCategoryId(Long categoryId, Integer startLimitFrom, Integer amountOnPage);

    /**
     * Checks if topic exists in category
     * @param topicId topic id
     * @param categoryId category id
     * @return true if topic exists
     *          false if topic doesn't exist
     */
    Boolean checkIfTopicExistsInCategory(Long topicId, Long categoryId);
}
