package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CollectionDAO;
import by.bsu.rfct.fclearn.entity.Collection;
import by.bsu.rfct.fclearn.entity.Topic;
import by.bsu.rfct.fclearn.entity.User;
import by.bsu.rfct.fclearn.service.CardService;
import by.bsu.rfct.fclearn.service.CollectionService;
import by.bsu.rfct.fclearn.service.TopicService;
import by.bsu.rfct.fclearn.service.UserService;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionConverter;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionConverterSmall;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTO;
import by.bsu.rfct.fclearn.service.dto.topic.TopicConverterSmall;
import by.bsu.rfct.fclearn.service.dto.topic.TopicDTO;
import by.bsu.rfct.fclearn.service.dto.topic.TopicDTOConverter;
import by.bsu.rfct.fclearn.service.dto.user.UserConverterSmall;
import by.bsu.rfct.fclearn.service.dto.user.UserDTO;
import by.bsu.rfct.fclearn.service.dto.user.UserDTOConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {

    private static final Logger LOG = LogManager.getLogger(CollectionServiceImpl.class);

    @Autowired
    private CollectionDAO collectionDAO;

    @Autowired
    private CollectionConverter collectionConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDTOConverter userDTOConverter;

    @Autowired
    private UserConverterSmall userConverterSmall;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicDTOConverter topicDTOConverter;

    @Autowired
    private TopicConverterSmall topicConverterSmall;

    @Autowired
    private CardService cardService;

    @Autowired
    private CollectionConverterSmall collectionConverterSmall;

    @Override
    public CollectionDTO create(CollectionDTO dto) {
        return null;
    }

    @Override
    public CollectionDTO read(Long id) {
        LOG.debug("CollectionService - read id={}", id);
        Collection collection = collectionDAO.read(id);
        CollectionDTO collectionDTO = collectionConverter.convert(collection);
        UserDTO authorDTO = userService.read(id);
        User author = userDTOConverter.convert(authorDTO);
        authorDTO = userConverterSmall.convert(author);
        collectionDTO.setAuthor(authorDTO);
        TopicDTO topicDTO = topicService.read(id);
        Topic topic = topicDTOConverter.convert(topicDTO);
        topicDTO = topicConverterSmall.convert(topic);
        collectionDTO.setTopic(topicDTO);
        collectionDTO.setCardsAmount(cardService.countCardAmountInCollection(id));
        return collectionDTO;
    }

    @Override
    public CollectionDTO update(CollectionDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CollectionDTO> readAll(Long pageNumber, Long amountOnPage) {
        LOG.debug("CollectionService - read all");
        List<CollectionDTO> collectionDTOs = new ArrayList<>();
        List<Collection> collections = collectionDAO.readAll(pageNumber, amountOnPage);
        for (Collection collection : collections) {
            CollectionDTO collectionDTO = collectionConverter.convert(collection);
            UserDTO authorDTO = userService.read(collection.getId());
            User author = userDTOConverter.convert(authorDTO);
            authorDTO = userConverterSmall.convert(author);
            collectionDTO.setAuthor(authorDTO);
            TopicDTO topicDTO = topicService.read(collection.getTopicId());
            Topic topic = topicDTOConverter.convert(topicDTO);
            topicDTO = topicConverterSmall.convert(topic);
            collectionDTO.setTopic(topicDTO);
            collectionDTO.setCardsAmount(cardService.countCardAmountInCollection(collection.getId()));
            collectionDTOs.add(collectionDTO);
        }
        return collectionDTOs;
    }

    @Override
    public Long countAll() {
        LOG.debug("CollectionService - count all");
        return collectionDAO.countAll();
    }

    @Override
    public List<CollectionDTO> readAllByTopicId(Long topicId) {
        LOG.debug("CollectionService - read all by topic id={}", topicId);
        List<Collection> collections = collectionDAO.readAllByTopicId(topicId);
        List<CollectionDTO> collectionDTOs = new ArrayList<>();
        for (Collection collection : collections) {
            CollectionDTO collectionDTO = collectionConverter.convert(collection);
            UserDTO authorDTO = userService.read(collection.getAuthorId());
            User author = userDTOConverter.convert(authorDTO);
            authorDTO = userConverterSmall.convert(author);
            collectionDTO.setAuthor(authorDTO);
            TopicDTO topicDTO = topicService.read(collection.getTopicId());
            Topic topic = topicDTOConverter.convert(topicDTO);
            topicDTO = topicConverterSmall.convert(topic);
            collectionDTO.setTopic(topicDTO);
            collectionDTO.setCardsAmount(cardService.countCardAmountInCollection(collection.getId()));
            collectionDTOs.add(collectionDTO);
        }
        return collectionDTOs;
    }
}
