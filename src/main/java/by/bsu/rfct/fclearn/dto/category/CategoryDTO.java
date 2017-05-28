package by.bsu.rfct.fclearn.dto.category;

import by.bsu.rfct.fclearn.dto.AbstractDTO;
import by.bsu.rfct.fclearn.dto.topic.TopicDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

public class CategoryDTO extends AbstractDTO<Long> {

    @NotBlank(message = "{validation.category.name.empty}")
    @Length(min = 1, max = 45, message = "{validation.category.name.length}")
    private String name;
    private String image;
    private Long topicAmount;
    private List<TopicDTO> topics;

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

    public List<TopicDTO> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDTO> topics) {
        this.topics = topics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(image, that.image) &&
                Objects.equals(topicAmount, that.topicAmount) &&
                Objects.equals(topics, that.topics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, image, topicAmount, topics);
    }
}
