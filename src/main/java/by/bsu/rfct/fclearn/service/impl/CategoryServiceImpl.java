package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.service.CategoryService;
import by.bsu.rfct.fclearn.service.dto.category.CategoryConverter;
import by.bsu.rfct.fclearn.service.dto.category.CategoryDTO;
import by.bsu.rfct.fclearn.service.dto.category.CategoryDTOConverter;
import by.bsu.rfct.fclearn.service.util.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOG = LogManager.getLogger(CategoryServiceImpl.class);

    private CategoryDAO categoryDAO;
    private CategoryConverter categoryConverter;
    private CategoryDTOConverter categoryDTOConverter;

    public CategoryServiceImpl(CategoryDAO categoryDAO, CategoryConverter categoryConverter,
                               CategoryDTOConverter categoryDTOConverter) {
        this.categoryDAO = categoryDAO;
        this.categoryConverter = categoryConverter;
        this.categoryDTOConverter = categoryDTOConverter;
    }

    @Override
    public Long create(CategoryDTO dto) {
        LOG.debug("CategoryService - create - category name={}", dto.getName());
        return categoryDAO.create(categoryDTOConverter.convert(dto));
    }

    @Override
    public CategoryDTO read(Long id) {
        LOG.debug("CategoryService - read by id={}", id);
        Category category = categoryDAO.read(id);
        CategoryDTO categoryDTO = categoryConverter.convert(category);
        categoryDTO.setTopicAmount(this.countTopicAmount(id));
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
    public List<CategoryDTO> readAll(Integer pageNumber, Integer amountOnPage) {
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
