package sns.testtask.eventconfigservice.exceptions.responses;

import lombok.Getter;

import java.util.StringJoiner;

@Getter
public class ExceptionResponse {
    private final String message;
    public ExceptionResponse(String message, String exMessage) {
        StringJoiner resultMessage = new StringJoiner(": ");
        resultMessage.add(message).add(exMessage);
        this.message = resultMessage.toString();
    }
}
