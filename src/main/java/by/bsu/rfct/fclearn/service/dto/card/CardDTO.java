package by.bsu.rfct.fclearn.service.dto.card;

import by.bsu.rfct.fclearn.service.dto.AbstractDTO;

import java.util.Objects;

public class CardDTO extends AbstractDTO<Long> {

    private String question;
    private String answer;
    private String questionImage;
    private String answerImage;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CardDTO cardDTO = (CardDTO) o;
        return Objects.equals(question, cardDTO.question) &&
                Objects.equals(answer, cardDTO.answer) &&
                Objects.equals(questionImage, cardDTO.questionImage) &&
                Objects.equals(answerImage, cardDTO.answerImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), question, answer, questionImage, answerImage);
    }
}
