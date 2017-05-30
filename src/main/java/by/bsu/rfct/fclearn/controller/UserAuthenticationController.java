package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.dto.MessageDTO;
import by.bsu.rfct.fclearn.dto.user.UserDTO;
import by.bsu.rfct.fclearn.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserAuthenticationController {

    /*@Value("${message.user.register}")
    private String successDeleteMessage;*/

    private UserService userService;

    public UserAuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity registration(@RequestBody @Valid UserDTO userDTO) {
        userDTO.setStatus("ACTIVE");
        userService.create(userDTO);

        return new ResponseEntity<>(new MessageDTO(HttpStatus.CREATED.value(), "User was successfully registered"),
                HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/success")
    public ResponseEntity success(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
