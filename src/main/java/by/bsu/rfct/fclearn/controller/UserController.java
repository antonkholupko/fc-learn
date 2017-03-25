package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.service.UserService;
import by.bsu.rfct.fclearn.service.dto.user.UserDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity findUsers(@RequestParam(name="page", defaultValue= ControllerUtils.DEFAULT_PAGE_NUMBER) int pageNumber,
                                    @RequestParam(name="size", defaultValue=ControllerUtils.DEFAULT_PAGE_SIZE) int pageSize) {

        pageNumber = ControllerUtils.validatePageNumber(pageNumber);
        pageSize = ControllerUtils.validatePageSize(pageSize);

        List<UserDTO> userDTOs = userService.readAll(pageNumber, pageSize);
        Long userAmount = userService.countAll();
        Integer totalPages = ControllerUtils.calculatePagesAmount(userAmount, pageSize);

        HttpHeaders headers = new HttpHeaders();
        PaginationHttpHeaders.addPaginationHeaders(headers, pageSize, pageNumber, userAmount, totalPages);

        return new ResponseEntity<>(userDTOs, headers, HttpStatus.OK);
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity findUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.read(id), HttpStatus.OK);
    }


}
