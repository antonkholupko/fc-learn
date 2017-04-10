package by.bsu.rfct.fclearn.dto.card;

import by.bsu.rfct.fclearn.entity.Card;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("cardConverterSmall")
public class CardConverterSmall implements Converter<Card, CardDTO> {

    @Override
    public CardDTO convert(Card card) {
        CardDTO cardDTO = new CardDTO();
        if (card != null) {
            cardDTO.setId(card.getId());
            cardDTO.setQuestion(card.getQuestion());
        }
        return cardDTO;
    }
}
