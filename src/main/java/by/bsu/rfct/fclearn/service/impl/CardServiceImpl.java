package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.service.CardService;
import by.bsu.rfct.fclearn.service.dto.CardDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cardService")
public class CardServiceImpl implements CardService{

    @Override
    public CardDTO create(CardDTO dto) {
        return null;
    }

    @Override
    public CardDTO read(Long id) {
        return null;
    }

    @Override
    public CardDTO update(CardDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CardDTO> readAll() {
        return null;
    }

    @Override
    public Long countAll() {
        return null;
    }
}
