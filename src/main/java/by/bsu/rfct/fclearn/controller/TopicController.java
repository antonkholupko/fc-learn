package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.service.TopicService;
import by.bsu.rfct.fclearn.service.dto.topic.TopicDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TopicController {

    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/categories/{categoryId:[\\d]+}/topics")
    public ResponseEntity findTopicsByCategoryId(@PathVariable("categoryId") Long categoryId,
                                    @RequestParam(name="page", defaultValue=ControllerUtils.DEFAULT_PAGE_NUMBER) int pageNumber,
                                     @RequestParam(name="size", defaultValue=ControllerUtils.DEFAULT_PAGE_SIZE) int pageSize) {

        pageNumber = ControllerUtils.validatePageNumber(pageNumber);
        pageSize = ControllerUtils.validatePageSize(pageSize);

        List<TopicDTO> topicDTOs = topicService.readAllByCategoryId(categoryId, pageNumber, pageSize);
        Long topicAmount = topicService.countAll();
        Integer totalPages = ControllerUtils.calculatePagesAmount(topicAmount, pageSize);

        HttpHeaders headers = new HttpHeaders();
        PaginationHttpHeaders.addPaginationHeaders(headers, pageSize, pageNumber, topicAmount, totalPages);

        return new ResponseEntity<>(topicDTOs, headers, HttpStatus.OK);
    }

    @GetMapping("/topics/{id:[\\d]+}")
    public ResponseEntity findTopicById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(topicService.read(id), HttpStatus.OK);
    }

}
