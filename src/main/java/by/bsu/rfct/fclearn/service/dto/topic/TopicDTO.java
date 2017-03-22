package by.bsu.rfct.fclearn.service.dto.topic;

import by.bsu.rfct.fclearn.service.dto.AbstractDTO;

import java.util.Objects;

public class TopicDTO extends AbstractDTO<Long> {

    private String name;
    private String image;
    private Long collectionAmount;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TopicDTO topicDTO = (TopicDTO) o;
        return Objects.equals(name, topicDTO.name) &&
                Objects.equals(image, topicDTO.image) &&
                Objects.equals(collectionAmount, topicDTO.collectionAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, image, collectionAmount);
    }
}
