package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.controller.dto.MessageDTO;
import by.bsu.rfct.fclearn.service.exception.EntityExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LogManager.getLogger(ControllerExceptionHandler.class);

    @Value("${error.404}")
    private String message404;

    @Value("{error.500}")
    private String message500;

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleEntityExistsException(EntityExistsException ex) {
        LOG.warn(ex);
        return prepareResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity prepareResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(new MessageDTO(status.value(), message), status);
    }

}
