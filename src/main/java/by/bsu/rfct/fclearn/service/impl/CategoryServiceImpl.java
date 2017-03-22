package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.service.CategoryService;
import by.bsu.rfct.fclearn.service.dto.category.CategoryConverter;
import by.bsu.rfct.fclearn.service.dto.category.CategoryDTO;
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

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private CategoryConverter categoryConverter;

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        return null;
    }

    @Override
    public CategoryDTO read(Long id) {
        LOG.debug("CategoryService - read by id={}", id);
        Category category = categoryDAO.read(id);
        return categoryConverter.convert(category);
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
            categoryDTOs.add(categoryConverter.convert(category));
        }
        return categoryDTOs;
    }

    @Override
    public Long countAll() {
        LOG.debug("CategoryService - count all");
        return categoryDAO.countAll();
    }
}
