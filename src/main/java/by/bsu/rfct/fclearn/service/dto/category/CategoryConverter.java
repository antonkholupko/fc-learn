package by.bsu.rfct.fclearn.service.dto.category;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("categoryConverter")
public class CategoryConverter implements Converter<Category, CategoryDTO>{

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public CategoryDTO convert(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        if (category != null) {
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setImage(category.getImage());
            categoryDTO.setTopicAmount(categoryDAO.countTopicAmount(category.getId()));
        }
        return categoryDTO;
    }
}
