package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.TopicDAO;
import by.bsu.rfct.fclearn.entity.Topic;
import by.bsu.rfct.fclearn.service.TopicService;
import by.bsu.rfct.fclearn.service.dto.topic.TopicConverter;
import by.bsu.rfct.fclearn.service.dto.topic.TopicDTO;
import by.bsu.rfct.fclearn.service.util.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("topicService")
public class TopicServiceImpl implements TopicService{

    private static final Logger LOG = LogManager.getLogger(TopicServiceImpl.class);

    @Autowired
    private TopicDAO topicDAO;

    @Autowired
    private TopicConverter topicConverter;

    @Override
    public TopicDTO create(TopicDTO dto) {
        return null;
    }

    @Override
    public TopicDTO read(Long id) {
        LOG.debug("TopicService - read by id={}", id);
        Topic topic = topicDAO.read(id);
        return topicConverter.convert(topic);
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
        LOG.debug("TopicService - read all");
        List<TopicDTO> topicDTOs = new ArrayList<>();
        List<Topic> topics = topicDAO.readAll(ServiceUtils.countStartLimitFrom(pageNumber, amountOnPage), amountOnPage);
        for (Topic topic : topics) {
            topicDTOs.add(topicConverter.convert(topic));
        }
        return topicDTOs;
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
