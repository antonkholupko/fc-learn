package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.dao.TopicDAO;
import by.bsu.rfct.fclearn.entity.Topic;
import by.bsu.rfct.fclearn.service.TopicService;
import by.bsu.rfct.fclearn.dto.topic.TopicConverter;
import by.bsu.rfct.fclearn.dto.topic.TopicDTO;
import by.bsu.rfct.fclearn.dto.topic.TopicDTOConverter;
import by.bsu.rfct.fclearn.service.exception.EntityExistsException;
import by.bsu.rfct.fclearn.service.util.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("topicService")
public class TopicServiceImpl implements TopicService{

    private static final Logger LOG = LogManager.getLogger(TopicServiceImpl.class);

    private TopicDAO topicDAO;
    private CategoryDAO categoryDAO;
    private TopicConverter topicConverter;
    private TopicDTOConverter topicDTOConverter;

    public TopicServiceImpl(TopicDAO topicDAO, TopicConverter topicConverter, TopicDTOConverter topicDTOConverter,
                            CategoryDAO categoryDAO) {
        this.topicDAO = topicDAO;
        this.categoryDAO = categoryDAO;
        this.topicConverter = topicConverter;
        this.topicDTOConverter = topicDTOConverter;
    }

    @Override
    public Long create(TopicDTO dto) {
        LOG.debug("TopicService - create topic name={}", dto.getName());
        if (!topicDAO.checkIfExist(topicDTOConverter.convert(dto))) {
            Long topicId = topicDAO.create(topicDTOConverter.convert(dto));
            if (topicDAO.checkIfTopicExistsInCategory(topicId, dto.getCategoryId())) {
                throw new EntityExistsException("Such topic exists in category");
            } else {
                categoryDAO.addTopic(dto.getCategoryId(), topicId);
            }
            return topicId;
        } else {
            throw new EntityExistsException("Such topic exists");
        }
    }

    @Override
    public TopicDTO read(Long id) {
        LOG.debug("TopicService - read by id={}", id);
        Topic topic = topicDAO.read(id);
        TopicDTO topicDTO = topicConverter.convert(topic);
        topicDTO.setCollectionAmount(topicDAO.countCollectionAmount(id));
        return topicDTO;
    }

    @Override
    public Long update(TopicDTO dto) {
        LOG.debug("TopicService - update topic id={}", dto.getId());
        return topicDAO.update(topicDTOConverter.convert(dto));
    }

    @Override
    public void delete(Long id) {
        LOG.debug("TopicService - delete topic id={}", id);
        topicDAO.delete(id);
    }

    @Override
    public List<TopicDTO> readAll(Integer pageNumber, Integer amountOnPage) {
        LOG.debug("TopicService - read all");
        throw new UnsupportedOperationException("TopicService - read all - unsupported method");
    }

    @Override
    public Long countAll() {
        LOG.debug("TopicService - count all");
        return topicDAO.countAll();
    }

    @Override
    public Long countCollectionAmount(Long topicId) {
        LOG.debug("TopicService - count amount of collections in topic id={}", topicId);
        return topicDAO.countCollectionAmount(topicId);
    }

    @Override
    public List<TopicDTO> readAllByCategoryId(Long categoryId, Integer pageNumber, Integer amountOnPage) {
        LOG.debug("TopicService - read all topics by category id={}", categoryId);
        List<TopicDTO> topicDTOs = new ArrayList<>();
        List<Topic> topics = topicDAO.readAllByCategoryId(categoryId,
                ServiceUtils.countStartLimitFrom(pageNumber, amountOnPage), amountOnPage);
        for (Topic topic : topics) {
            TopicDTO topicDTO = topicConverter.convert(topic);
            topicDTO.setCollectionAmount(topicDAO.countCollectionAmount(topic.getId()));
            topicDTOs.add(topicDTO);
        }
        return topicDTOs;
    }
}
