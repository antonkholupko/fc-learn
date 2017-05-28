package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.dto.MessageDTO;
import by.bsu.rfct.fclearn.dto.collection.CollectionDTO;
import by.bsu.rfct.fclearn.service.CollectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories/{categoryId:[\\d]+}/topics/{topicId:[\\d]+}/collections")
public class CollectionController {

    private static final String COLLECTION_PATH = "%s/collections/%s";

    private CollectionService collectionService;

    @Value("${collection.created}")
    private String messageCreated;

    @Value("${collection.deleted}")
    private String deleteMessage;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public ResponseEntity findCollectionsByTopicId(@PathVariable("topicId") Long topicId,
                                                   @RequestParam(name = "page", defaultValue = ControllerUtils.DEFAULT_PAGE_NUMBER) int pageNumber,
                                                   @RequestParam(name = "size", defaultValue = ControllerUtils.DEFAULT_PAGE_SIZE) int pageSize) {

        pageNumber = ControllerUtils.validatePageNumber(pageNumber);
        pageSize = ControllerUtils.validatePageSize(pageSize);

        List<CollectionDTO> collectionDTOs = collectionService.readAllByTopicId(topicId, pageNumber, pageSize);
        Long collectionAmount = collectionService.countAll();
        Integer totalPages = ControllerUtils.calculatePagesAmount(collectionAmount, pageSize);

        HttpHeaders headers = new HttpHeaders();
        PaginationHttpHeaders.addPaginationHeaders(headers, pageSize, pageNumber, collectionAmount, totalPages);

        return new ResponseEntity<>(collectionDTOs, headers, HttpStatus.OK);
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity findCollectionById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(collectionService.read(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createCollection(@PathVariable("topicId") Long topicId, @RequestBody @Valid CollectionDTO collectionDTO,
                                           @RequestHeader String host) {
        collectionDTO.setTopicId(topicId);
        Long createdCollectionId = collectionService.create(collectionDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, String.format(COLLECTION_PATH, host, createdCollectionId));
        return new ResponseEntity<>(String.format(messageCreated, createdCollectionId), headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity updateCollection(@PathVariable("topicId") Long topicId, @PathVariable("id") Long id,
                                           @RequestBody @Valid CollectionDTO collectionDTO) {
        collectionDTO.setTopicId(topicId);
        collectionDTO.setId(id);
        return new ResponseEntity<>(collectionService.update(collectionDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity deleteCollection(@PathVariable("id") Long id) {
        collectionService.delete(id);
        return new ResponseEntity<>(new MessageDTO(HttpStatus.OK.value(), deleteMessage), HttpStatus.OK);
    }
}
