package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dto.card.CardDTO;

import java.util.List;

public interface CardService extends GenericService<CardDTO, Long> {

    Long countCardAmountInCollection(Long collectionId);

    List<CardDTO> readAllCardsByCollectionId(Long collectionId, Integer pageNumber, Integer amountOnPage);

}
