package by.bsu.rfct.fclearn.entity;

import java.util.Objects;

public class Topic extends AbstractEntity{

    private Long courseId;
    private String name;
    private String description;
    private String image;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        Topic topic = (Topic) o;
        return Objects.equals(courseId, topic.courseId) &&
                Objects.equals(name, topic.name) &&
                Objects.equals(description, topic.description) &&
                Objects.equals(image, topic.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), courseId, name, description, image);
    }
}
