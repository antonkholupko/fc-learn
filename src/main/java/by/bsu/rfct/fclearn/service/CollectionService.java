package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTO;

import java.util.List;

public interface CollectionService extends GenericService<CollectionDTO, Long> {

    List<CollectionDTO> readAllByTopicId(Long topicId);

}
