package by.bsu.rfct.fclearn.service.dto.topic;

import by.bsu.rfct.fclearn.dao.TopicDAO;
import by.bsu.rfct.fclearn.entity.Topic;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("topicConverter")
public class TopicConverter implements Converter<Topic, TopicDTO> {

    private TopicDAO topicDAO;

    public TopicConverter(TopicDAO topicDAO) {
        this.topicDAO = topicDAO;
    }

    @Override
    public TopicDTO convert(Topic topic) {
        TopicDTO topicDTO = new TopicDTO();
        if (topic != null) {
            topicDTO.setId(topic.getId());
            topicDTO.setName(topic.getName());
            topicDTO.setImage(topic.getImage());
        }
        return topicDTO;
    }
}
