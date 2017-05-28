package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.dto.MessageDTO;
import by.bsu.rfct.fclearn.dto.card.CardDTO;
import by.bsu.rfct.fclearn.service.CardService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories/{categoryId:[\\d]+}/topics/{topicId:[\\d]+}/collections/{collectionId:[\\d]+}/cards")
public class CardController {

    private static final String CARD_PATH = "%s/cards/%s";

    @Value("${card.created}")
    private String messageCreated;

    @Value("${card.deleted}")
    private String deleteMessage;

    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity findCards(@PathVariable("collectionId") Long collectionId,
                                    @RequestParam(name = "page", defaultValue = ControllerUtils.DEFAULT_PAGE_NUMBER) int pageNumber,
                                    @RequestParam(name = "size", defaultValue = ControllerUtils.DEFAULT_PAGE_SIZE) int pageSize) {

        pageNumber = ControllerUtils.validatePageNumber(pageNumber);
        pageSize = ControllerUtils.validatePageSize(pageSize);

        List<CardDTO> cardDTOs = cardService.readAllCardsByCollectionId(collectionId, pageNumber, pageSize);
        Long cardAmount = cardService.countAll();
        Integer totalPages = ControllerUtils.calculatePagesAmount(cardAmount, pageSize);

        HttpHeaders headers = new HttpHeaders();
        PaginationHttpHeaders.addPaginationHeaders(headers, pageSize, pageNumber, cardAmount, totalPages);

        return new ResponseEntity<>(cardDTOs, headers, HttpStatus.OK);
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity findCardById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cardService.read(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createCard(@PathVariable("collectionId") Long collectionId, @RequestBody @Valid CardDTO cardDTO,
                                     @RequestHeader String host) {
        cardDTO.setCollectionId(collectionId);
        Long createdCardId = cardService.create(cardDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, String.format(CARD_PATH, host, createdCardId));
        return new ResponseEntity<>(String.format(messageCreated, createdCardId), headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity updateCard(@PathVariable("collectionId") Long collectionId, @PathVariable("id") Long id,
                                     @RequestBody @Valid CardDTO cardDTO) {
        cardDTO.setCollectionId(collectionId);
        cardDTO.setId(id);
        return new ResponseEntity<>(cardService.update(cardDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity deleteCard(@PathVariable("id") Long id) {
        cardService.delete(id);
        return new ResponseEntity<>(new MessageDTO(HttpStatus.OK.value(), deleteMessage), HttpStatus.OK);
    }
}
