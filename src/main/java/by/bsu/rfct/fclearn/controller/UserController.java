package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.util.ControllerUtils;
import by.bsu.rfct.fclearn.controller.util.PaginationHttpHeaders;
import by.bsu.rfct.fclearn.dto.MessageDTO;
import by.bsu.rfct.fclearn.dto.user.UserDTO;
import by.bsu.rfct.fclearn.service.CollectionService;
import by.bsu.rfct.fclearn.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final String USER_PATH = "%s/users/%s";

    @Value("${user.created}")
    private String messageCreated;

    @Value("${user.deleted}")
    private String deleteMessage;

    private UserService userService;
    private CollectionService collectionService;

    public UserController(UserService userService, CollectionService collectionService) {
        this.userService = userService;
        this.collectionService = collectionService;
    }

    @GetMapping
    public ResponseEntity findUsers(@RequestParam(name = "page", defaultValue = ControllerUtils.DEFAULT_PAGE_NUMBER) int pageNumber,
                                    @RequestParam(name = "size", defaultValue = ControllerUtils.DEFAULT_PAGE_SIZE) int pageSize) {

        pageNumber = ControllerUtils.validatePageNumber(pageNumber);
        pageSize = ControllerUtils.validatePageSize(pageSize);

        List<UserDTO> userDTOs = userService.readAll(pageNumber, pageSize);
        Long userAmount = userService.countAll();
        Integer totalPages = ControllerUtils.calculatePagesAmount(userAmount, pageSize);

        HttpHeaders headers = new HttpHeaders();
        PaginationHttpHeaders.addPaginationHeaders(headers, pageSize, pageNumber, userAmount, totalPages);

        return new ResponseEntity<>(userDTOs, headers, HttpStatus.OK);
    }

   /* @GetMapping("/{id:[\\d]+}")
    public ResponseEntity findUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.read(id), HttpStatus.OK);
    }*/

    @GetMapping("/{name}")
    public ResponseEntity findUserByName(@PathVariable("name") String name) {
        UserDTO userDTO = userService.getByName(name);
        userDTO.setSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity registerUser(@RequestBody @Valid UserDTO userDTO, @RequestHeader String host) {
        Long createdUserId = userService.create(userDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, String.format(USER_PATH, host, createdUserId));
        return new ResponseEntity<>(String.format(messageCreated, createdUserId), headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserDTO userDTO) {
        userDTO.setId(id);
        return new ResponseEntity<>(userService.update(userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(new MessageDTO(HttpStatus.OK.value(), deleteMessage), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(new UserDTO(userService.loginUser(userDTO)), HttpStatus.OK);
    }

    @GetMapping("/{userId:[\\d]+}/collections/{collectionId:[\\d]+}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addCollection(@PathVariable("userId") Long userId, @PathVariable("collectionId") Long collectionId) {
        userService.addCollection(userId, collectionId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{userId:[\\d]+}/collections")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getCollections(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(collectionService.getCollectionsByUserId(userId), HttpStatus.OK);
    }

}
