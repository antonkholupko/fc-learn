package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dto.card.CardDTO;

public interface TrainingService {

    CardDTO getNextCard(Long userId, Long collectionId);
}
