package pers.nefedov.subscriptions.exception;

public class DataRetrievalException extends RuntimeException {
    public DataRetrievalException(String couldNotRetrieveSubscriptions, Exception e) {
        super(couldNotRetrieveSubscriptions, e);
    }
}
