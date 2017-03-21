package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.service.CollectionService;
import by.bsu.rfct.fclearn.service.dto.CollectionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {

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
        return null;
    }
}
