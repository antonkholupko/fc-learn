package by.bsu.rfct.fclearn.service.dto.topic;

import by.bsu.rfct.fclearn.service.dto.AbstractDTO;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTO;

import java.util.List;
import java.util.Objects;

public class TopicDTO extends AbstractDTO<Long> {

    private String name;
    private String image;
    private Long collectionAmount;
    private List<CollectionDTO> collections;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCollectionAmount() {
        return collectionAmount;
    }

    public void setCollectionAmount(Long collectionAmount) {
        this.collectionAmount = collectionAmount;
    }

    public List<CollectionDTO> getCollections() {
        return collections;
    }

    public void setCollections(List<CollectionDTO> collections) {
        this.collections = collections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TopicDTO topicDTO = (TopicDTO) o;
        return Objects.equals(name, topicDTO.name) &&
                Objects.equals(image, topicDTO.image) &&
                Objects.equals(collectionAmount, topicDTO.collectionAmount) &&
                Objects.equals(collections, topicDTO.collections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, image, collectionAmount, collections);
    }
}
