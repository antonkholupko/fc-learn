package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.service.dto.topic.TopicDTO;

public interface TopicService extends GenericService<TopicDTO, Long> {

    Long countCollectionAmount(Long topicId);

}
