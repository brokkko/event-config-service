package sns.testtask.eventconfigservice.domain.filters;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventConfigFilter {
    private String eventType;
    private String source;
    private Boolean enabled;
}
