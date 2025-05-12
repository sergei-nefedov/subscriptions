package pers.nefedov.subscriptions.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String emailAlreadyInUse) {
        super(emailAlreadyInUse);
    }
}
