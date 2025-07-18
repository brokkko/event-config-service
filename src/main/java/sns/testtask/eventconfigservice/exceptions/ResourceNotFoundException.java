package sns.testtask.eventconfigservice.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message, String id) {
        super(message);
    }
}
