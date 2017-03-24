package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.dao.TopicDAO;
import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.entity.Collection;
import by.bsu.rfct.fclearn.entity.Topic;
import by.bsu.rfct.fclearn.service.CollectionService;
import by.bsu.rfct.fclearn.service.TopicService;
import by.bsu.rfct.fclearn.service.dto.category.CategoryConverterSmall;
import by.bsu.rfct.fclearn.service.dto.category.CategoryDTO;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionConverter;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionConverterSmall;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTO;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTOConverter;
import by.bsu.rfct.fclearn.service.dto.topic.TopicConverter;
import by.bsu.rfct.fclearn.service.dto.topic.TopicConverterSmall;
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
    private CategoryDAO categoryDAO;

    @Autowired
    private TopicConverter topicConverter;

    @Autowired
    private CategoryConverterSmall categoryConverterSmall;

    @Autowired
    private TopicConverterSmall topicConverterSmall;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private CollectionConverterSmall collectionConverterSmall;

    @Autowired
    private CollectionDTOConverter collectionDTOConverter;

    @Override
    public TopicDTO create(TopicDTO dto) {
        return null;
    }

    @Override
    public TopicDTO read(Long id) {
        //TODO
        LOG.debug("TopicService - read by id={}", id);
        Topic topic = topicDAO.read(id);
        TopicDTO topicDTO = topicConverter.convert(topic);
        List<CollectionDTO> collectionDTOs = collectionService.readAllByTopicId(topic.getId());
        List<CollectionDTO> smallCollectionDTOs = new ArrayList<>();
        for (CollectionDTO collectionDTO : collectionDTOs) {
            Collection collection = collectionDTOConverter.convert(collectionDTO);
            collectionDTO = collectionConverterSmall.convert(collection);
            smallCollectionDTOs.add(collectionDTO);
        }
        topicDTO.setCollections(smallCollectionDTOs);
        return topicDTO;
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
            TopicDTO topicDTO = topicConverter.convert(topic);
            topicDTOs.add(topicDTO);
        }
        return topicDTOs;
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
    public List<TopicDTO> readAllByCategoryId(Long categoryId, Long pageNumber, Long amountOnPage) {
        LOG.debug("TopicService - read all topics by category id={}", categoryId);
        List<TopicDTO> topicDTOs = new ArrayList<>();
        List<Topic> topics = topicDAO.readAllByCategoryId(categoryId,
                ServiceUtils.countStartLimitFrom(pageNumber, amountOnPage), amountOnPage);
        for (Topic topic : topics) {
            TopicDTO topicDTO = topicConverter.convert(topic);
            topicDTOs.add(topicDTO);
        }
        return topicDTOs;
    }
}
