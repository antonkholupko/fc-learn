package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CollectionDAO;
import by.bsu.rfct.fclearn.service.CollectionService;
import by.bsu.rfct.fclearn.service.dto.CollectionDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {

    private static final Logger LOG = LogManager.getLogger(CollectionServiceImpl.class);

    @Autowired
    private CollectionDAO collectionDAO;

    @Override
    public CollectionDTO create(CollectionDTO dto) {
        return null;
    }

    @Override
    public CollectionDTO read(Long id) {
        return null;
    }

    @Override
    public CollectionDTO update(CollectionDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CollectionDTO> readAll() {
        return null;
    }

    @Override
    public Long countAll() {
        LOG.debug("CollectionService - count all");
        return collectionDAO.countAll();
    }

    @Override
    public Boolean checkIfExists(CollectionDTO dto) {
        return null;
    }
}
