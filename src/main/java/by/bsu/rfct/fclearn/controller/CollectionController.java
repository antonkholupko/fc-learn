package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.service.CollectionService;
import by.bsu.rfct.fclearn.service.dto.collection.CollectionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    @Autowired
    CollectionService collectionService;

    @GetMapping("/topic/{topicId:[\\d]+}")
    public ResponseEntity findCollectionsByTopicId(@PathVariable("topicId") Long topicId,
            @RequestParam(name="page", defaultValue= ControllerUtils.DEFAULT_PAGE_NUMBER) long pageNumber,
            @RequestParam(name="size", defaultValue=ControllerUtils.DEFAULT_PAGE_SIZE) long pageSize) {

        pageNumber = ControllerUtils.validatePageNumber(pageNumber);
        pageSize = ControllerUtils.validatePageSize(pageSize);

        List<CollectionDTO> collectionDTOs = collectionService.readAllByTopicId(topicId, pageNumber, pageSize);
        Long collectionAmount = collectionService.countAll();
        Long totalPages = ControllerUtils.calculatePagesAmount(collectionAmount, pageSize);

        HttpHeaders headers = new HttpHeaders();
        PaginationHttpHeaders.addPaginationHeaders(headers, pageSize, pageNumber, collectionAmount, totalPages);

        return new ResponseEntity<>(collectionDTOs, headers, HttpStatus.OK);
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity findCollectionById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(collectionService.read(id), HttpStatus.OK);
    }

}
