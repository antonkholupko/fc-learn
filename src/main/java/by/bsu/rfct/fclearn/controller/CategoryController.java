package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.service.CategoryService;
import by.bsu.rfct.fclearn.service.dto.category.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity findCategories(@RequestParam(name = "page", defaultValue = ControllerUtils.DEFAULT_PAGE_NUMBER) long pageNumber,
                                      @RequestParam(name = "size", defaultValue = ControllerUtils.DEFAULT_PAGE_SIZE) long pageSize) {

        pageNumber = ControllerUtils.validatePageNumber(pageNumber);
        pageSize = ControllerUtils.validatePageSize(pageSize);

        List<CategoryDTO> categoryDTOList = categoryService.readAll(pageNumber, pageSize);
        Long categoryCount = categoryService.countAll();
        Long totalPages = ControllerUtils.calculatePagesAmount(categoryCount, pageSize);

        HttpHeaders headers = new HttpHeaders();
        PaginationHttpHeaders.addPaginationHeaders(headers, pageSize, pageNumber, categoryCount, totalPages);

        return new ResponseEntity<>(categoryDTOList, headers, HttpStatus.OK);
    }

}
