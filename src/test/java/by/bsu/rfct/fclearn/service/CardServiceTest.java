package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dao.CardDAO;
import by.bsu.rfct.fclearn.service.impl.CardServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceTest {

    @InjectMocks
    private CardServiceImpl cardService;
    @Mock
    private CardDAO cardDAO;

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
        Long expectedCardsAmount = 49323L;
        when(cardDAO.countAll()).thenReturn(expectedCardsAmount);
        Long cardsAmount = cardService.countAll();
        verify(cardDAO, times(1)).countAll();
        assertEquals(expectedCardsAmount, cardsAmount);
    }

}