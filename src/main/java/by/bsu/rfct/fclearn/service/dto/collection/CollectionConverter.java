package by.bsu.rfct.fclearn.service.dto.collection;

import by.bsu.rfct.fclearn.dao.CardDAO;
import by.bsu.rfct.fclearn.dao.TopicDAO;
import by.bsu.rfct.fclearn.dao.UserDAO;
import by.bsu.rfct.fclearn.entity.Collection;
import by.bsu.rfct.fclearn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("collectionConverter")
public class CollectionConverter implements Converter<Collection, CollectionDTO>{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TopicDAO topicDAO;

    @Autowired
    private CardDAO cardDAO;

    @Override
    public CollectionDTO convert(Collection collection) {
        CollectionDTO collectionDTO = new CollectionDTO();
        User author = userDAO.read(collection.getAuthorId());
        author.setPassword("");
        if (collection != null) {
            collectionDTO.setId(collection.getId());
            collectionDTO.setName(collection.getName());
            collectionDTO.setDescription(collection.getDescription());
            collectionDTO.setCreated(collection.getCreated());
            collectionDTO.setModified(collection.getModified());
            collectionDTO.setImage(collection.getImage());
            collectionDTO.setStatus(collection.getStatus().toString());
            collectionDTO.setRating(collection.getRating());
            collectionDTO.setAuthor(author);
            collectionDTO.setTopic(topicDAO.read(collection.getTopicId()));
            collectionDTO.setCardsAmount(cardDAO.countCardAmountInCollection(collection.getId()));
        }
        return collectionDTO;
    }
}
