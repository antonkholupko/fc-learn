package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dto.category.CategoryDTO;

public interface CategoryService extends GenericService<CategoryDTO, Long> {

    Long countTopicAmount(Long categoryId);

}
