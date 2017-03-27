package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CollectionDAO;
import by.bsu.rfct.fclearn.entity.Collection;
import by.bsu.rfct.fclearn.entity.User;
import by.bsu.rfct.fclearn.service.CardService;
import by.bsu.rfct.fclearn.service.CollectionService;
import by.bsu.rfct.fclearn.service.UserService;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionConverter;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTO;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTOConverter;
import by.bsu.rfct.fclearn.service.dto.user.UserConverterSmall;
import by.bsu.rfct.fclearn.service.dto.user.UserDTO;
import by.bsu.rfct.fclearn.service.dto.user.UserDTOConverter;
import by.bsu.rfct.fclearn.service.exception.EntityExistsException;
import by.bsu.rfct.fclearn.service.util.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {

    private static final Logger LOG = LogManager.getLogger(CollectionServiceImpl.class);

    private CollectionDAO collectionDAO;
    private CollectionConverter collectionConverter;
    private UserService userService;
    private UserDTOConverter userDTOConverter;
    private UserConverterSmall userConverterSmall;
    private CardService cardService;
    private CollectionDTOConverter collectionDTOConverter;

    public CollectionServiceImpl(CollectionDAO collectionDAO, CollectionConverter collectionConverter,
                                 UserService userService, UserDTOConverter userDTOConverter,
                                 UserConverterSmall userConverterSmall, CardService cardService,
                                 CollectionDTOConverter collectionDTOConverter) {
        this.collectionDAO = collectionDAO;
        this.collectionConverter = collectionConverter;
        this.userService = userService;
        this.userDTOConverter = userDTOConverter;
        this.userConverterSmall = userConverterSmall;
        this.cardService = cardService;
        this.collectionDTOConverter = collectionDTOConverter;
    }

    @Override
    public Long create(CollectionDTO dto) {
        LOG.debug("CollectionService - create collection name={}", dto.getName());
        Collection collection = collectionDTOConverter.convert(dto);
        LocalDateTime createdTime = LocalDateTime.now();
        collection.setCreated(createdTime);
        collection.setModified(createdTime);
        if (!collectionDAO.checkIfExist(collection)) {
            return collectionDAO.create(collection);
        } else {
            throw new EntityExistsException("Such collection exists");
        }
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
        return collectionDTO;
    }

    @Override
    public Long update(CollectionDTO dto) {
        LOG.debug("CollectionService - update id={}", dto.getId());
        LocalDateTime modifiedTime = LocalDateTime.now();
        dto.setModified(modifiedTime);
        return collectionDAO.update(collectionDTOConverter.convert(dto));
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CollectionDTO> readAll(Integer pageNumber, Integer amountOnPage) {
        LOG.debug("CollectionService - read all");
        throw new UnsupportedOperationException("CollectionService - read all  - unsupported method");
    }

    @Override
    public Long countAll() {
        LOG.debug("CollectionService - count all");
        return collectionDAO.countAll();
    }

    @Override
    public List<CollectionDTO> readAllByTopicId(Long topicId, Integer pageNumber, Integer amountOnPage) {
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

    @Override
    public Long countByAuthorId(Long authorId) {
        LOG.debug("CollectionService - count by author id");
        return collectionDAO.countByAuthorId(authorId);
    }
}
