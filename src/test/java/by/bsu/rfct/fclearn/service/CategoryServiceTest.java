package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.service.impl.CategoryServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryDAO categoryDAO;

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