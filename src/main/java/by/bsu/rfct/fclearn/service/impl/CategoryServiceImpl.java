package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.dto.category.CategoryConverter;
import by.bsu.rfct.fclearn.dto.category.CategoryDTO;
import by.bsu.rfct.fclearn.dto.category.CategoryDTOConverter;
import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.service.CategoryService;
import by.bsu.rfct.fclearn.service.exception.EntityExistsException;
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
        if (!categoryDAO.checkIfExist(categoryDTOConverter.convert(dto))) {
            return categoryDAO.create(categoryDTOConverter.convert(dto));
        } else {
            throw new EntityExistsException("Such category exists");
        }
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
    public Long update(CategoryDTO dto) {
        LOG.debug("CategoryService - update category id={}", dto.getId());
        return categoryDAO.update(categoryDTOConverter.convert(dto));
    }

    @Override
    public void delete(Long id) {
        LOG.debug("CategoryService - delete category id={}", id);
        categoryDAO.delete(id);
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
