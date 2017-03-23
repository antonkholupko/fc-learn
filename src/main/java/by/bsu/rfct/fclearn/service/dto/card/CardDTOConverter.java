package by.bsu.rfct.fclearn.service.dto.card;

import by.bsu.rfct.fclearn.entity.Card;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("cardDTOConverter")
public class CardDTOConverter implements Converter<CardDTO, Card> {

    @Override
    public Card convert(CardDTO cardDTO) {
        Card card = new Card();
        if (cardDTO != null) {
            card.setId(cardDTO.getId());
            card.setQuestion(cardDTO.getQuestion());
            card.setAnswer(cardDTO.getAnswer());
            card.setQuestionImage(cardDTO.getQuestionImage());
            card.setAnswerImage(cardDTO.getAnswerImage());
        }
        return card;
    }
}
