package by.bsu.rfct.fclearn.service.dto.card;

import by.bsu.rfct.fclearn.service.dto.AbstractDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Objects;

public class CardDTO extends AbstractDTO<Long> {

    @NotBlank(message = "{validation.card.question.empty}")
    @Length(min = 1, max = 4500, message = "{validation.card.question.length}")
    private String question;

    @NotBlank(message = "{validation.card.answer.empty}")
    @Length(min = 1, max = 4500, message = "{validation.card.answer.length}")
    private String answer;

    private String questionImage;

    private String answerImage;

    private Long collectionId;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }

    public String getAnswerImage() {
        return answerImage;
    }

    public void setAnswerImage(String answerImage) {
        this.answerImage = answerImage;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
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
        CardDTO cardDTO = (CardDTO) o;
        return Objects.equals(question, cardDTO.question) &&
                Objects.equals(answer, cardDTO.answer) &&
                Objects.equals(questionImage, cardDTO.questionImage) &&
                Objects.equals(answerImage, cardDTO.answerImage) &&
                Objects.equals(collectionId, cardDTO.collectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), question, answer, questionImage, answerImage, collectionId);
    }
}
