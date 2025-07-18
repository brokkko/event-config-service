package sns.testtask.eventconfigservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sns.testtask.eventconfigservice.exceptions.responses.ExceptionResponse;

/**
 * Exception handler for the EventConfigService.
 * Catches and processes common exceptions thrown by controllers,
 * converting them into appropriate HTTP responses with structured bodies.
 */
@ControllerAdvice
@Slf4j
public class EventConfigExceptionHandlerController {

    /**
     * Handle validation failures from @Valid annotated request bodies.
     *
     * @param ex the exception containing binding and field error details
     * @return a 400 Bad Request response with an ExceptionResponse body
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        log.warn("{} : Validation failed exception was handled", ex.getClass());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse("Validation failed", ex.getMessage()));
    }

    /**
     * Handle cases where a requested resource is not found.
     *
     * @param ex the exception indicating the missing resource and its identifier
     * @return a 404 Not Found response with an ExceptionResponse body
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        log.warn("{} : Not found exception was handled", ex.getClass());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse("Not Found", ex.getMessage()));
    }

    /**
     * Handle conflicts arising from duplicate event configuration attempts.
     *
     * @param ex the exception indicating a uniqueness violation
     * @return a 409 Conflict response with an ExceptionResponse body
     */
    @ExceptionHandler(DuplicateEventConfigException.class)
    public ResponseEntity<?> handleConflict(DuplicateEventConfigException ex) {
        log.warn("{} : Conflict exception was handled", ex.getClass());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ExceptionResponse("Conflict", ex.getMessage()));
    }

    /**
     * Fallback handler for any uncaught exceptions.
     *
     * @param ex the exception that was not handled by more specific methods
     * @return a 500 Internal Server Error response with an ExceptionResponse body
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception ex) {
        log.warn("{} : Internal Server Error exception was handled", ex.getClass());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse("Internal Server Error", ex.getMessage()));
    }

}
