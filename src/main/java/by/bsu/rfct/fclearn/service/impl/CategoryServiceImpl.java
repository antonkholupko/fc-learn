package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.dao.TopicDAO;
import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.entity.Topic;
import by.bsu.rfct.fclearn.service.CategoryService;
import by.bsu.rfct.fclearn.service.TopicService;
import by.bsu.rfct.fclearn.service.dto.category.CategoryConverter;
import by.bsu.rfct.fclearn.service.dto.category.CategoryDTO;
import by.bsu.rfct.fclearn.service.dto.topic.TopicConverterSmall;
import by.bsu.rfct.fclearn.service.dto.topic.TopicDTO;
import by.bsu.rfct.fclearn.service.dto.topic.TopicDTOConverter;
import by.bsu.rfct.fclearn.service.util.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{

    private static final Logger LOG = LogManager.getLogger(CategoryServiceImpl.class);

    private static final Long TOPIC_LIST_STARTS = 1L;
    private static final Long TOPIC_LIST_SIZE = 5L;

    private CategoryDAO categoryDAO;
    private CategoryConverter categoryConverter;
    private TopicService topicService;
    private TopicConverterSmall topicConverterSmall;
    private TopicDTOConverter topicDTOConverter;
    private TopicDAO topicDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO, CategoryConverter categoryConverter, TopicService topicService,
                               TopicConverterSmall topicConverterSmall, TopicDTOConverter topicDTOConverter,
                               TopicDAO topicDAO) {
        this.categoryDAO = categoryDAO;
        this.categoryConverter = categoryConverter;
        this.topicService = topicService;
        this.topicConverterSmall = topicConverterSmall;
        this.topicDTOConverter = topicDTOConverter;
        this.topicDAO = topicDAO;
    }

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        return null;
    }

    @Override
    public CategoryDTO read(Long id) {
        LOG.debug("CategoryService - read by id={}", id);
        Category category = categoryDAO.read(id);
        CategoryDTO categoryDTO = categoryConverter.convert(category);
        categoryDTO.setTopicAmount(this.countTopicAmount(id));
        List<TopicDTO> smallTopicDTOs = new ArrayList<>();
        List<TopicDTO> topicDTOs = topicService.readAllByCategoryId(id, TOPIC_LIST_STARTS, TOPIC_LIST_SIZE);
        topicDTOs.forEach(topicDTO -> {
            Topic topic = topicDTOConverter.convert(topicDTO);
            topicDTO.setCollectionAmount(topicDAO.countCollectionAmount(topicDTO.getId()));
            topicDTO = topicConverterSmall.convert(topic);
            smallTopicDTOs.add(topicDTO);
        });
        categoryDTO.setTopics(smallTopicDTOs);
        return categoryDTO;
    }

    @Override
    public CategoryDTO update(CategoryDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CategoryDTO> readAll(Long pageNumber, Long amountOnPage) {
        LOG.debug("CategoryService - read all");
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        List<Category> categories = categoryDAO.readAll(ServiceUtils.countStartLimitFrom(pageNumber, amountOnPage),
                amountOnPage);
        for (Category category : categories) {
            CategoryDTO categoryDTO = categoryConverter.convert(category);
            categoryDTO.setTopicAmount(this.countTopicAmount(category.getId()));
            categoryDTOs.add(categoryDTO);
        }
        return categoryDTOs;
    }

    @Override
    public Long countAll() {
        LOG.debug("CategoryService - count all");
        return categoryDAO.countAll();
    }

    @Override
    public Long countTopicAmount(Long categoryId) {
        LOG.debug("CategoryService - count topic amount");
        return categoryDAO.countTopicAmount(categoryId);
    }
}
