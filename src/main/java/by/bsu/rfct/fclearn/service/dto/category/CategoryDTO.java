package by.bsu.rfct.fclearn.service.dto.category;

import by.bsu.rfct.fclearn.dao.CategoryDAO;
import by.bsu.rfct.fclearn.service.dto.AbstractDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class CategoryDTO extends AbstractDTO<Long> {

    private String name;
    private String image;
    private Long topicAmount;

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

    public Long getTopicAmount() {
        return topicAmount;
    }

    public void setTopicAmount(Long topicAmount) {
        this.topicAmount = topicAmount;
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
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(image, that.image) &&
                Objects.equals(topicAmount, that.topicAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, image, topicAmount);
    }
}
