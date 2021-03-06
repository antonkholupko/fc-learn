package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dto.topic.TopicDTO;

import java.util.List;

public interface TopicService extends GenericService<TopicDTO, Long> {

    Long countCollectionAmount(Long topicId);

    List<TopicDTO> readAllByCategoryId(Long categoryId, Integer pageNumber, Integer amountOnPage);

}
