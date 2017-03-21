package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.service.CategoryService;
import by.bsu.rfct.fclearn.service.dto.CategoryDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<CategoryDTO> readAll() {
        return null;
    }

    @Override
    public Long countAll() {
        LOG.debug("CategoryService - count all");
        return categoryDAO.countAll();
    }
}
