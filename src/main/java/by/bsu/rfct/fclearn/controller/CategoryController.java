package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.service.CategoryService;
import by.bsu.rfct.fclearn.service.dto.category.CategoryDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

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
}
