package by.bsu.rfct.fclearn.entity;

import java.util.Objects;

public class Course extends AbstractEntity {

    private Long categoryId;
    private String name;
    private String image;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

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
        Course course = (Course) o;
        return Objects.equals(categoryId, course.categoryId) &&
                Objects.equals(name, course.name) &&
                Objects.equals(image, course.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), categoryId, name, image);
    }
}
