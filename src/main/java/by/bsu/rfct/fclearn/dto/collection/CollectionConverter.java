package by.bsu.rfct.fclearn.dto.collection;

import by.bsu.rfct.fclearn.entity.Collection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("collectionConverter")
public class CollectionConverter implements Converter<Collection, CollectionDTO> {

    @Override
    public CollectionDTO convert(Collection collection) {
        CollectionDTO collectionDTO = new CollectionDTO();
        if (collection != null) {
            collectionDTO.setId(collection.getId());
            collectionDTO.setName(collection.getName());
            collectionDTO.setDescription(collection.getDescription());
            collectionDTO.setCreated(collection.getCreated());
            collectionDTO.setModified(collection.getModified());
            collectionDTO.setImage(collection.getImage());
            collectionDTO.setStatus(collection.getStatus().toString());
            collectionDTO.setRating(collection.getRating());
        }
        return collectionDTO;
    }
}
