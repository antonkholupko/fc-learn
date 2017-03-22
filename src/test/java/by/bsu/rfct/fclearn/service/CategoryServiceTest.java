package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.entity.Category;
import by.bsu.rfct.fclearn.service.dto.category.CategoryConverter;
import by.bsu.rfct.fclearn.service.dto.category.CategoryDTO;
import by.bsu.rfct.fclearn.service.impl.CategoryServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryDAO categoryDAO;
    @Mock
    private CategoryConverter categoryConverter;

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testRead() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testReadAll() throws Exception {
        int expectedCategoriesAmount = 1;
        Category expectedCategory = new Category();
        expectedCategory.setId(3L);
        expectedCategory.setName("Name");
        expectedCategory.setImage("Image");
        CategoryDTO expectedCategoryDTO = new CategoryDTO();
        expectedCategoryDTO.setId(3L);
        expectedCategoryDTO.setName("Name");
        expectedCategoryDTO.setImage("Image");
        List<Category> categories = new ArrayList<>();
        categories.add(expectedCategory);
        when(categoryDAO.readAll(0L, 1L)).thenReturn(categories);
        when(categoryConverter.convert(expectedCategory)).thenReturn(expectedCategoryDTO);
        when(categoryDAO.countTopicAmount(3L)).thenReturn(3L);
        List<CategoryDTO> categoryDTOs = categoryService.readAll(1L, 1L);
        verify(categoryDAO, times(1)).readAll(0L,1L);
        CategoryDTO categoryDTO = categoryDTOs.get(0);
        assertEquals(expectedCategory.getId(), categoryDTO.getId());
        assertEquals(expectedCategory.getName(), categoryDTO.getName());
        assertEquals(expectedCategory.getImage(), categoryDTO.getImage());
        assertEquals(expectedCategoriesAmount, categoryDTOs.size());
    }

    @Test
    public void testCountAll() throws Exception {
        Long expectedCategoriesAmount = 20L;
        when(categoryDAO.countAll()).thenReturn(expectedCategoriesAmount);
        Long categoriesAmount = categoryService.countAll();
        verify(categoryDAO, times(1)).countAll();
        assertEquals(expectedCategoriesAmount, categoriesAmount);
    }

}