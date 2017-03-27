package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.service.CollectionService;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CollectionController {

    private static final String COLLECTION_PATH = "%s/collections/%s";

    private CollectionService collectionService;

    @Value("${collection.created}")
    private String messageCreated;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/topics/{topicId:[\\d]+}/collections")
    public ResponseEntity findCollectionsByTopicId(@PathVariable("topicId") Long topicId,
            @RequestParam(name="page", defaultValue= ControllerUtils.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(name="size", defaultValue=ControllerUtils.DEFAULT_PAGE_SIZE) int pageSize) {

        pageNumber = ControllerUtils.validatePageNumber(pageNumber);
        pageSize = ControllerUtils.validatePageSize(pageSize);

        List<CollectionDTO> collectionDTOs = collectionService.readAllByTopicId(topicId, pageNumber, pageSize);
        Long collectionAmount = collectionService.countAll();
        Integer totalPages = ControllerUtils.calculatePagesAmount(collectionAmount, pageSize);

        HttpHeaders headers = new HttpHeaders();
        PaginationHttpHeaders.addPaginationHeaders(headers, pageSize, pageNumber, collectionAmount, totalPages);

        return new ResponseEntity<>(collectionDTOs, headers, HttpStatus.OK);
    }

    @GetMapping("/collections/{id:[\\d]+}")
    public ResponseEntity findCollectionById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(collectionService.read(id), HttpStatus.OK);
    }

    @PostMapping("/collections")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createCollection(@RequestBody @Valid CollectionDTO collectionDTO, @RequestHeader String host) {
        Long createdCollectionId = collectionService.create(collectionDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, String.format(COLLECTION_PATH, host, createdCollectionId));
        return new ResponseEntity<>(String.format(messageCreated, createdCollectionId), headers, HttpStatus.CREATED);
    }

    @PutMapping("/collections/{id:[\\d]+}")
    public ResponseEntity updateCollection(@PathVariable("id") Long id, @RequestBody @Valid CollectionDTO collectionDTO) {
        collectionDTO.setId(id);
        return new ResponseEntity<>(collectionService.update(collectionDTO), HttpStatus.OK);
    }

}
