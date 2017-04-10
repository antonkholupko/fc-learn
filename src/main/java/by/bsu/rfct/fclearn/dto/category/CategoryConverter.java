package by.bsu.rfct.fclearn.dto.category;

import by.bsu.rfct.fclearn.entity.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("categoryConverter")
public class CategoryConverter implements Converter<Category, CategoryDTO>{

    @Override
    public CategoryDTO convert(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        if (category != null) {
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setImage(category.getImage());
        }
        return categoryDTO;
    }
}
