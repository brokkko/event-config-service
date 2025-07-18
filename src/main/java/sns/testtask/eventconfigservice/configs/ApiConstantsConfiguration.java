package sns.testtask.eventconfigservice.configs;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
@Getter
public class ApiConstantsConfiguration {
    public static final String API = "/api/v1";
    public static final String VALIDATION = API + "/event-config";
}
