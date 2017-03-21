package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.TopicDAO;
import by.bsu.rfct.fclearn.service.TopicService;
import by.bsu.rfct.fclearn.service.dto.TopicDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("topicService")
public class TopicServiceImpl implements TopicService{

    private static final Logger LOG = LogManager.getLogger(TopicServiceImpl.class);

    @Autowired
    private TopicDAO topicDAO;

    @Override
    public TopicDTO create(TopicDTO dto) {
        return null;
    }

    @Override
    public TopicDTO read(Long id) {
        return null;
    }

    @Override
    public TopicDTO update(TopicDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<TopicDTO> readAll(Long pageNumber, Long amountOnPage) {
        return null;
    }

    @Override
    public Long countAll() {
        LOG.debug("TopicService - count all");
        return topicDAO.countAll();
    }

    @Override
    public Boolean checkIfExists(TopicDTO dto) {
        return null;
    }
}
