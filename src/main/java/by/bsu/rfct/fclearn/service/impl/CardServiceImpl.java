package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CardDAO;
import by.bsu.rfct.fclearn.service.CardService;
import by.bsu.rfct.fclearn.service.dto.CardDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cardService")
public class CardServiceImpl implements CardService{

    private static final Logger LOG = LogManager.getLogger(CardServiceImpl.class);

    @Autowired
    private CardDAO cardDAO;

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
        LOG.debug("CardService - count all");
        return cardDAO.countAll();
    }

    @Override
    public Boolean checkIfExists(CardDTO dto) {
        return null;
    }
}
