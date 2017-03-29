package by.bsu.rfct.fclearn.service.exception;

public class CannotLoginUserException extends RuntimeException{

    public CannotLoginUserException() {
    }

    public CannotLoginUserException(String message) {
        super(message);
    }
}
