package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.service.CardService;
import by.bsu.rfct.fclearn.service.dto.card.CardDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CardController {

    private static final String CARD_PATH = "%s/cards/%s";

    @Value("${card.created}")
    private String messageCreated;

    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/collections/{collectionId:[\\d]+}/cards")
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

    @GetMapping("/cards/{id:[\\d]+}")
    public ResponseEntity findCardById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cardService.read(id), HttpStatus.OK);
    }

    @PostMapping("/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createCard(@RequestBody @Valid CardDTO cardDTO, @RequestHeader String host) {
        Long createdCardId = cardService.create(cardDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, String.format(CARD_PATH, host, createdCardId));
        return new ResponseEntity<>(String.format(messageCreated, createdCardId), headers, HttpStatus.CREATED);
    }


}
