package sns.testtask.eventconfigservice.exceptions;

public class DuplicateEventConfigException extends RuntimeException {
    public DuplicateEventConfigException(String message) {
        super(message);
    }
}
