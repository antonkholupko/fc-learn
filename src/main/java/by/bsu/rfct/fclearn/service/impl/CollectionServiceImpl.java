package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CollectionDAO;
import by.bsu.rfct.fclearn.entity.Collection;
import by.bsu.rfct.fclearn.service.CollectionService;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionConverter;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {

    private static final Logger LOG = LogManager.getLogger(CollectionServiceImpl.class);

    @Autowired
    private CollectionDAO collectionDAO;

    @Autowired
    private CollectionConverter collectionConverter;

    @Override
    public CollectionDTO create(CollectionDTO dto) {
        return null;
    }

    @Override
    public CollectionDTO read(Long id) {
        LOG.debug("CollectionService - read id={}", id);
        Collection collection = collectionDAO.read(id);
        return collectionConverter.convert(collection);
    }

    @Override
    public CollectionDTO update(CollectionDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CollectionDTO> readAll(Long pageNumber, Long amountOnPage) {
        LOG.debug("CollectionService - read all");
        List<CollectionDTO> collectionDTOs = new ArrayList<>();
        List<Collection> collections = collectionDAO.readAll(pageNumber, amountOnPage);
        for (Collection collection : collections) {
            collectionDTOs.add(collectionConverter.convert(collection));
        }
        return collectionDTOs;
    }

    @Override
    public Long countAll() {
        LOG.debug("CollectionService - count all");
        return collectionDAO.countAll();
    }
}
