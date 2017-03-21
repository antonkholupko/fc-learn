package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dao.TopicDAO;
import by.bsu.rfct.fclearn.service.impl.TopicServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest {

    @InjectMocks
    private TopicServiceImpl topicService;
    @Mock
    private TopicDAO topicDAO;

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
        Long expectedTopicsAmount = 67L;
        when(topicDAO.countAll()).thenReturn(expectedTopicsAmount);
        Long topicsAmount = topicService.countAll();
        verify(topicDAO, times(1)).countAll();
        assertEquals(expectedTopicsAmount, topicsAmount);
    }

}