package by.bsu.rfct.fclearn.service.dto.category;

import by.bsu.rfct.fclearn.entity.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("categoryDTOConverter")
public class CategoryDTOConverter implements Converter<CategoryDTO, Category> {

    @Override
    public Category convert(CategoryDTO categoryDTO) {
        Category category = new Category();
        if (categoryDTO != null) {
            category.setId(categoryDTO.getId());
            category.setName(categoryDTO.getName());
            category.setImage(categoryDTO.getImage());
        }
        return category;
    }
}
