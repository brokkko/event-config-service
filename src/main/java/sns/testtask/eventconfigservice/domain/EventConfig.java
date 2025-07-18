package sns.testtask.eventconfigservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventConfig {
    @Id
    private String id;
    private String eventType;
    private String source;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}