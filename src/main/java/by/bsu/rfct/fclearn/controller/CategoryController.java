package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.dto.MessageDTO;
import by.bsu.rfct.fclearn.dto.category.CategoryDTO;
import by.bsu.rfct.fclearn.service.CategoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static final String CATEGORY_PATH = "%s/categories/%s";

    @Value("${category.created}")
    private String messageCreated;

    @Value("${category.deleted}")
    private String deleteMessage;

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity findCategories(@RequestParam(name = "page", defaultValue = ControllerUtils.DEFAULT_PAGE_NUMBER) int pageNumber,
                                         @RequestParam(name = "size", defaultValue = ControllerUtils.DEFAULT_PAGE_SIZE) int pageSize) {

        pageNumber = ControllerUtils.validatePageNumber(pageNumber);
        pageSize = ControllerUtils.validatePageSize(pageSize);

        List<CategoryDTO> categoryDTOList = categoryService.readAll(pageNumber, pageSize);
        Long categoryAmount = categoryService.countAll();
        Integer totalPages = ControllerUtils.calculatePagesAmount(categoryAmount, pageSize);

        HttpHeaders headers = new HttpHeaders();
        PaginationHttpHeaders.addPaginationHeaders(headers, pageSize, pageNumber, categoryAmount, totalPages);

        return new ResponseEntity<>(categoryDTOList, headers, HttpStatus.OK);
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity findCategoryById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(categoryService.read(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createCategory(@RequestBody @Valid CategoryDTO categoryDTO, @RequestHeader String host) {
        Long createdCategoryId = categoryService.create(categoryDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, String.format(CATEGORY_PATH, host, createdCategoryId));
        return new ResponseEntity<>(String.format(messageCreated, createdCategoryId), headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity updateCategory(@PathVariable("id") Long id, @RequestBody @Valid CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        return new ResponseEntity<>(categoryService.update(categoryDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>(new MessageDTO(HttpStatus.OK.value(), deleteMessage), HttpStatus.OK);
    }
}
