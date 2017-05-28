package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.dto.MessageDTO;
import by.bsu.rfct.fclearn.dto.topic.TopicDTO;
import by.bsu.rfct.fclearn.service.TopicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories/{categoryId:[\\d]+}/topics")
public class TopicController {

    private static final String TOPIC_PATH = "%s/topics/%s";

    @Value("${topic.created}")
    private String messageCreated;

    @Value("${topic.deleted}")
    private String deleteMessage;

    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity findTopicsByCategoryId(@PathVariable("categoryId") Long categoryId,
                                                 @RequestParam(name = "page", defaultValue = ControllerUtils.DEFAULT_PAGE_NUMBER) int pageNumber,
                                                 @RequestParam(name = "size", defaultValue = ControllerUtils.DEFAULT_PAGE_SIZE) int pageSize) {

        pageNumber = ControllerUtils.validatePageNumber(pageNumber);
        pageSize = ControllerUtils.validatePageSize(pageSize);

        List<TopicDTO> topicDTOs = topicService.readAllByCategoryId(categoryId, pageNumber, pageSize);
        Long topicAmount = topicService.countAll();
        Integer totalPages = ControllerUtils.calculatePagesAmount(topicAmount, pageSize);

        HttpHeaders headers = new HttpHeaders();
        PaginationHttpHeaders.addPaginationHeaders(headers, pageSize, pageNumber, topicAmount, totalPages);

        return new ResponseEntity<>(topicDTOs, headers, HttpStatus.OK);
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity findTopicById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(topicService.read(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createTopic(@PathVariable("categoryId") Long categoryId, @RequestBody @Valid TopicDTO topicDTO,
                                      @RequestHeader String host) {
        topicDTO.setCategoryId(categoryId);
        Long createdTopicId = topicService.create(topicDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, String.format(TOPIC_PATH, host, createdTopicId));
        return new ResponseEntity<>(String.format(messageCreated, createdTopicId), headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity updateTopic(@PathVariable("categoryId") Long categoryId, @PathVariable("id") Long id,
                                      @RequestBody @Valid TopicDTO topicDTO) {
        topicDTO.setCategoryId(categoryId);
        topicDTO.setId(id);
        return new ResponseEntity<>(topicService.update(topicDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity deleteTopic(@PathVariable("id") Long id) {
        topicService.delete(id);
        return new ResponseEntity<>(new MessageDTO(HttpStatus.OK.value(), deleteMessage), HttpStatus.OK);
    }

}
