package by.bsu.rfct.fclearn.service.dto.collection;

import by.bsu.rfct.fclearn.entity.Collection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("collectionConverterSmall")
public class CollectionConverterSmall implements Converter<Collection, CollectionDTO>{

    @Override
    public CollectionDTO convert(Collection collection) {
        CollectionDTO collectionDTO = new CollectionDTO();
        if (collection != null) {
            collectionDTO.setId(collection.getId());
            collectionDTO.setName(collection.getName());
        }
        return collectionDTO;
    }
}
