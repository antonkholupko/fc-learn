package by.bsu.rfct.fclearn.service.dto.topic;

import by.bsu.rfct.fclearn.service.dto.AbstractDTO;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.Objects;

public class TopicDTO extends AbstractDTO<Long> {

    @NotBlank(message="{validation.topic.name.empty}")
    @Length(min=1, max=45, message = "{validation.topic.name.length}")
    private String name;
    @URL(message = "{validation.topic.image.invalid}")
    @Length(min=1, max=500, message = "{validation.topic.image.length}")
    private String image;
    private Long collectionAmount;
    @NotBlank(message = "{validation.topic.status.empty}")
    private String status;
    private Long categoryId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<CollectionDTO> getCollections() {
        return collections;
    }

    public void setCollections(List<CollectionDTO> collections) {
        this.collections = collections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TopicDTO topicDTO = (TopicDTO) o;
        return Objects.equals(name, topicDTO.name) &&
                Objects.equals(image, topicDTO.image) &&
                Objects.equals(collectionAmount, topicDTO.collectionAmount) &&
                Objects.equals(status, topicDTO.status) &&
                Objects.equals(categoryId, topicDTO.categoryId) &&
                Objects.equals(collections, topicDTO.collections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, image, collectionAmount, status, categoryId, collections);
    }
}
