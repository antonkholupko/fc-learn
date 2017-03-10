package by.bsu.rfct.fclearn.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Collection extends AbstractEntity{

    public enum Status {
        PRIVATE, REQ, PUBLIC
    }

    private Long authorId;
    private Long topicId;
    private String name;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String image;
    private Collection.Status status;
    private Integer rating;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

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
        this.created = created.withNano(0);
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified.withNano(0);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatusString (String statusString) {
        this.status = Status.valueOf(statusString.toUpperCase().trim());
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        if (!super.equals(o)){
            return false;
        }
        Collection that = (Collection) o;
        return Objects.equals(authorId, that.authorId) &&
                Objects.equals(topicId, that.topicId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified) &&
                Objects.equals(image, that.image) &&
                status == that.status &&
                Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), authorId, topicId, name, description, created, modified, image, status, rating);
    }
}
