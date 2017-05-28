package by.bsu.rfct.fclearn.entity;

import java.util.Objects;

public class Card extends AbstractEntity<Long> {

    private Long collectionId;
    private String question;
    private String answer;
    private String questionImage;
    private String answerImage;

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Card card = (Card) o;
        return Objects.equals(collectionId, card.collectionId) &&
                Objects.equals(question, card.question) &&
                Objects.equals(answer, card.answer) &&
                Objects.equals(questionImage, card.questionImage) &&
                Objects.equals(answerImage, card.answerImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), collectionId, question, answer, questionImage, answerImage);
    }

    public enum Status {
        NEW, LOW, MEDIUM, HIGH
    }
}
