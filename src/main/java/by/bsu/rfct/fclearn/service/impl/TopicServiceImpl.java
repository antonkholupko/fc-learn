package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.service.TopicService;
import by.bsu.rfct.fclearn.service.dto.TopicDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("topicService")
public class TopicServiceImpl implements TopicService{

    @Override
    public TopicDTO create(TopicDTO dto) {
        return null;
    }

    @Override
    public TopicDTO read(Long id) {
        return null;
    }

    @Override
    public TopicDTO update(TopicDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<TopicDTO> readAll() {
        return null;
    }

    @Override
    public Long countAll() {
        return null;
    }
}
