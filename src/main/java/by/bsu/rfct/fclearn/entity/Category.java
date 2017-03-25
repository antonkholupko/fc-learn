package by.bsu.rfct.fclearn.entity;

import java.util.Objects;

public class Category extends AbstractEntity<Long> {

    private String name;
    private String image;

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
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        if (!super.equals(o)){
            return false;
        }
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(image, category.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, image);
    }
}
