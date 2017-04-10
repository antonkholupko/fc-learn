package by.bsu.rfct.fclearn.dto.topic;

import by.bsu.rfct.fclearn.entity.Topic;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("topicDTO")
public class TopicDTOConverter implements Converter<TopicDTO, Topic>{

    @Override
    public Topic convert(TopicDTO topicDTO) {
        Topic topic = new Topic();
        if (topicDTO != null) {
            topic.setId(topicDTO.getId());
            topic.setName(topicDTO.getName());
            topic.setImage(topicDTO.getImage());
            topic.setStatus(Topic.Status.valueOf(topicDTO.getStatus()));
        }
        return topic;
    }
}
