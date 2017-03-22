package by.bsu.rfct.fclearn.service.dto.topic;

import by.bsu.rfct.fclearn.entity.Topic;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("topicConverterSmall")
public class TopicConverterSmall implements Converter<Topic, TopicDTO> {

    @Override
    public TopicDTO convert(Topic topic) {
        TopicDTO topicDTO = new TopicDTO();
        if (topic != null) {
            topicDTO.setId(topic.getId());
            topicDTO.setName(topic.getName());
        }
        return topicDTO;
    }
}
