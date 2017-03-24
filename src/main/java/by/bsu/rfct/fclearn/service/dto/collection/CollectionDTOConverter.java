package by.bsu.rfct.fclearn.service.dto.collection;

import by.bsu.rfct.fclearn.entity.Collection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("collectionDTOConverter")
public class CollectionDTOConverter implements Converter<CollectionDTO, Collection>{

    @Override
    public Collection convert(CollectionDTO collectionDTO) {
        Collection collection = new Collection();
        if (collectionDTO != null) {
            collection.setId(collectionDTO.getId());
            //collection.setAuthorId(collectionDTO.getAuthor().getId());
            //collection.setTopicId(collectionDTO.getTopic().getId());
            collection.setName(collectionDTO.getName());
            collection.setDescription(collectionDTO.getDescription());
            collection.setCreated(collectionDTO.getCreated());
            collection.setModified(collectionDTO.getModified());
            collection.setImage(collectionDTO.getImage());
            collection.setStatus(Collection.Status.valueOf(collectionDTO.getStatus()));
            collection.setRating(collectionDTO.getRating());
        }
        return collection;
    }
}
