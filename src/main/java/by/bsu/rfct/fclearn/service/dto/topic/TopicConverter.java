package by.bsu.rfct.fclearn.service.dto.topic;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.dao.TopicDAO;
import by.bsu.rfct.fclearn.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("topicConverter")
public class TopicConverter implements Converter<Topic, TopicDTO> {

    @Autowired
    private TopicDAO topicDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public TopicDTO convert(Topic topic) {
        TopicDTO topicDTO = new TopicDTO();
        if (topic != null) {
            topicDTO.setId(topic.getId());
            topicDTO.setName(topic.getName());
            topicDTO.setImage(topic.getImage());
            topicDTO.setCollectionAmount(topicDAO.countCollectionAmount(topic.getId()));
            topicDTO.setCategories(categoryDAO.readCategoriesByTopicId(topic.getId()));
        }
        return topicDTO;
    }
}
