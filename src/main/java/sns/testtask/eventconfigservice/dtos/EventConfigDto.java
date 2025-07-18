package sns.testtask.eventconfigservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventConfigDto {
    private String id;

    @NotBlank(message = "eventType is mandatory")
    private String eventType;

    @NotBlank(message = "source is mandatory")
    private String source;

    @NotNull(message = "enabled must be specified")
    private Boolean enabled;
}
