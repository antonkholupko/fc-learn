package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dao.CollectionDAO;
import by.bsu.rfct.fclearn.service.impl.CollectionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CollectionServiceTest {

    @InjectMocks
    private CollectionServiceImpl collectionService;
    @Mock
    private CollectionDAO collectionDAO;

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
        Long expectedCollectionsAmount = 445L;
        when(collectionDAO.countAll()).thenReturn(expectedCollectionsAmount);
        Long collectionsAmount = collectionService.countAll();
        verify(collectionDAO, times(1)).countAll();
        assertEquals(expectedCollectionsAmount, collectionsAmount);
    }

}