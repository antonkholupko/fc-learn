package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.dao.TopicDAO;
import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.entity.Topic;
import by.bsu.rfct.fclearn.service.TopicService;
import by.bsu.rfct.fclearn.service.dto.category.CategoryConverterSmall;
import by.bsu.rfct.fclearn.service.dto.category.CategoryDTO;
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
    private CategoryDAO categoryDAO;

    @Autowired
    private TopicConverter topicConverter;

    @Autowired
    private CategoryConverterSmall categoryConverterSmall;

    @Override
    public TopicDTO create(TopicDTO dto) {
        return null;
    }

    @Override
    public TopicDTO read(Long id) {
        LOG.debug("TopicService - read by id={}", id);
        Topic topic = topicDAO.read(id);
        TopicDTO topicDTO = topicConverter.convert(topic);
        List<Category> categories = categoryDAO.readCategoriesByTopicId(id);
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for(Category category : categories) {
            categoryDTOs.add(categoryConverterSmall.convert(category));
        }
        topicDTO.setCategories(categoryDTOs);
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
            List<Category> categories = categoryDAO.readCategoriesByTopicId(topic.getId());
            List<CategoryDTO> categoryDTOs = new ArrayList<>();
            for (Category category : categories) {
                categoryDTOs.add(categoryConverterSmall.convert(category));
            }
            TopicDTO topicDTO = topicConverter.convert(topic);
            topicDTO.setCategories(categoryDTOs);
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
}
