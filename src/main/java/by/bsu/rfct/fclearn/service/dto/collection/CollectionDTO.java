package by.bsu.rfct.fclearn.service.dto.collection;

import by.bsu.rfct.fclearn.entity.Topic;
import by.bsu.rfct.fclearn.entity.User;
import by.bsu.rfct.fclearn.service.dto.AbstractDTO;

import java.time.LocalDateTime;
import java.util.Objects;

public class CollectionDTO extends AbstractDTO<Long> {

    private String name;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String image;
    private String status;
    private Integer rating;
    private User author;
    private Topic topic;
    private Long cardsAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getCardsAmount() {
        return cardsAmount;
    }

    public void setCardsAmount(Long cardsAmount) {
        this.cardsAmount = cardsAmount;
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
        CollectionDTO that = (CollectionDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified) &&
                Objects.equals(image, that.image) &&
                Objects.equals(status, that.status) &&
                Objects.equals(rating, that.rating) &&
                Objects.equals(author, that.author) &&
                Objects.equals(topic, that.topic) &&
                Objects.equals(cardsAmount, that.cardsAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, created, modified, image, status, rating, author, topic, cardsAmount);
    }
}
