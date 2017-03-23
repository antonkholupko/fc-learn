package by.bsu.rfct.fclearn.service.dto.card;

import by.bsu.rfct.fclearn.entity.Card;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("cardConverter")
public class CardConverter implements Converter<Card, CardDTO> {

    @Override
    public CardDTO convert(Card card) {
        CardDTO cardDTO = new CardDTO();
        if (card != null) {
            cardDTO.setId(card.getId());
            cardDTO.setQuestion(card.getQuestion());
            cardDTO.setAnswer(card.getAnswer());
            cardDTO.setQuestionImage(card.getQuestionImage());
            cardDTO.setAnswerImage(card.getAnswerImage());
        }
        return cardDTO;
    }
}
