package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CollectionDAO;
import by.bsu.rfct.fclearn.entity.Card;
import by.bsu.rfct.fclearn.entity.Collection;
import by.bsu.rfct.fclearn.entity.Topic;
import by.bsu.rfct.fclearn.entity.User;
import by.bsu.rfct.fclearn.service.CardService;
import by.bsu.rfct.fclearn.service.CollectionService;
import by.bsu.rfct.fclearn.service.TopicService;
import by.bsu.rfct.fclearn.service.UserService;
import by.bsu.rfct.fclearn.service.dto.card.CardConverterSmall;
import by.bsu.rfct.fclearn.service.dto.card.CardDTO;
import by.bsu.rfct.fclearn.service.dto.card.CardDTOConverter;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionConverter;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionConverterSmall;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTO;
import by.bsu.rfct.fclearn.service.dto.topic.TopicConverterSmall;
import by.bsu.rfct.fclearn.service.dto.topic.TopicDTO;
import by.bsu.rfct.fclearn.service.dto.topic.TopicDTOConverter;
import by.bsu.rfct.fclearn.service.dto.user.UserConverterSmall;
import by.bsu.rfct.fclearn.service.dto.user.UserDTO;
import by.bsu.rfct.fclearn.service.dto.user.UserDTOConverter;
import by.bsu.rfct.fclearn.service.util.ServiceUtils;
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
    private CardService cardService;

    @Autowired
    private CollectionConverterSmall collectionConverterSmall;

    @Autowired
    private CardDTOConverter cardDTOConverter;

    @Autowired
    private CardConverterSmall cardConverterSmall;

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
        collectionDTO.setCardsAmount(cardService.countCardAmountInCollection(id));
        List<CardDTO> cardDTOs = cardService.readAllCardsByCollectionId(id, 1L, 9L);
        List<CardDTO> smallCardDTOs = new ArrayList<>();
        for (CardDTO cardDTO : cardDTOs) {
            Card card = cardDTOConverter.convert(cardDTO);
            cardDTO = cardConverterSmall.convert(card);
            smallCardDTOs.add(cardDTO);
        }
        collectionDTO.setCards(smallCardDTOs);
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
        /*List<Collection> collections = collectionDAO.readAll(pageNumber, amountOnPage);
        for (Collection collection : collections) {
            CollectionDTO collectionDTO = collectionConverter.convert(collection);
            UserDTO authorDTO = userService.read(collection.getId());
            User author = userDTOConverter.convert(authorDTO);
            authorDTO = userConverterSmall.convert(author);
            collectionDTO.setAuthor(authorDTO);
            collectionDTO.setCardsAmount(cardService.countCardAmountInCollection(collection.getId()));
            collectionDTOs.add(collectionDTO);
        }*/
        return collectionDTOs;
    }

    @Override
    public Long countAll() {
        LOG.debug("CollectionService - count all");
        return collectionDAO.countAll();
    }

    @Override
    public List<CollectionDTO> readAllByTopicId(Long topicId, Long pageNumber, Long amountOnPage) {
        LOG.debug("CollectionService - read all by topic id={}", topicId);
        List<CollectionDTO> collectionDTOs = new ArrayList<>();
        List<Collection> collections = collectionDAO.readAllByTopicId(topicId,
                ServiceUtils.countStartLimitFrom(pageNumber, amountOnPage), amountOnPage);
        for (Collection collection : collections) {
            CollectionDTO collectionDTO = collectionConverter.convert(collection);
            collectionDTO.setCardsAmount(cardService.countCardAmountInCollection(collection.getId()));
            collectionDTOs.add(collectionDTO);
        }
        return collectionDTOs;
    }
}
