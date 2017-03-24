package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.service.dto.card.CardDTO;

import java.util.List;

public interface CardService extends GenericService<CardDTO, Long> {

    Long countCardAmountInCollection(Long collectionId);

    List<CardDTO> readAllCardsByCollectionId(Long collectionId, Long pageNumber, Long amountOnPage);

}
