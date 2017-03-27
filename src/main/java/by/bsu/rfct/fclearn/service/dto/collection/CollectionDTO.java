package by.bsu.rfct.fclearn.service.dto.collection;

import by.bsu.rfct.fclearn.service.dto.AbstractDTO;
import by.bsu.rfct.fclearn.service.dto.card.CardDTO;
import by.bsu.rfct.fclearn.service.dto.user.UserDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CollectionDTO extends AbstractDTO<Long> {

    @NotBlank(message = "{validation.collection.name.empty}")
    @Length(min = 1, max = 300, message = "{validation.collection.name.length}")
    private String name;

    @NotBlank(message = "{validation.collection.description.empty}")
    @Length(min = 1, max = 1500, message = "{validation.collection.description.length}")
    private String description;

    private LocalDateTime created;

    private LocalDateTime modified;

    @URL(message = "{validation.collection.image.invalid}")
    @Length
    private String image;

    @NotBlank(message = "{validation.collection.staus.empty}")
    private String status;

    private Integer rating;

    private UserDTO author;

    private List<CardDTO> cards;

    private Long cardsAmount;

    private Long topicId;

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

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }

    public Long getCardsAmount() {
        return cardsAmount;
    }

    public void setCardsAmount(Long cardsAmount) {
        this.cardsAmount = cardsAmount;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CollectionDTO that = (CollectionDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified) &&
                Objects.equals(image, that.image) &&
                Objects.equals(status, that.status) &&
                Objects.equals(rating, that.rating) &&
                Objects.equals(author, that.author) &&
                Objects.equals(cards, that.cards) &&
                Objects.equals(cardsAmount, that.cardsAmount) &&
                Objects.equals(topicId, that.topicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, created, modified, image, status, rating, author, cards, cardsAmount, topicId);
    }
}
