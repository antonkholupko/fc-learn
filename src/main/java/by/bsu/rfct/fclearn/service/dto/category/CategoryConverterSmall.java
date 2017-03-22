package by.bsu.rfct.fclearn.service.dto.category;

import by.bsu.rfct.fclearn.entity.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("categoryConverterSmall")
public class CategoryConverterSmall implements Converter<Category, CategoryDTO>{

    @Override
    public CategoryDTO convert(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        if (category != null) {
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
        }
        return categoryDTO;
    }
}
