package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.service.CategoryService;
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

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        return null;
    }

    @Override
    public CategoryDTO read(Long id) {
        return null;
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
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        List<Category> categories = categoryDAO.readAll(ServiceUtils.countStartLimitFrom(pageNumber, amountOnPage),
                amountOnPage);
        for (Category category : categories) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setImage(category.getImage());
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
    public Boolean checkIfExists(CategoryDTO dto) {
        return null;
    }
}
