package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dto.collection.CollectionDTO;

import java.util.List;

public interface CollectionService extends GenericService<CollectionDTO, Long> {

    List<CollectionDTO> readAllByTopicId(Long topicId, Integer pageNumber, Integer amountOnPage);

    Long countByAuthorId(Long authorId);

}
