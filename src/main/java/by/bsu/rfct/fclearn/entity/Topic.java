package by.bsu.rfct.fclearn.entity;

import java.util.Objects;

public class Topic extends AbstractEntity<Long> {

    private String name;
    private String image;
    private Status status;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatusString(String statusString) {
        if (statusString != null) {
            this.status = Status.valueOf(statusString.toUpperCase().trim());
        }
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
        Topic topic = (Topic) o;
        return Objects.equals(name, topic.name) &&
                Objects.equals(image, topic.image) &&
                status == topic.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, image, status);
    }

    public enum Status {
        PRIVATE, PUBLIC, REQ
    }
}
