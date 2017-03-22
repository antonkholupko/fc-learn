package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.entity.Category;

import java.util.List;

public interface CategoryDAO extends GenericDAO<Category, Long>{

    /**
     * Adds topic to category
     * @param topicId topic id
     * @param categoryId category id
     */
    void addTopic(Long categoryId, Long topicId);

    /**
     * Counts how much topics in category
     * @param categoryId category id
     * @return topic amount in category
     */
    Long countTopicAmount(Long categoryId);

    List<Category> readCategoriesByTopicId(Long topicId);

}
