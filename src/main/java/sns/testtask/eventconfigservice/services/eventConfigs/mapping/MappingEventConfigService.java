package sns.testtask.eventconfigservice.services.eventConfigs.mapping;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import sns.testtask.eventconfigservice.domain.EventConfig;
import sns.testtask.eventconfigservice.dtos.EventConfigDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MappingEventConfigService  extends ModelMapper {
    public EventConfig mapToEventConfig(EventConfigDto dto) {
        return map(dto, EventConfig.class);
    }

    public EventConfigDto mapToEventConfigDto(EventConfig dto) {
        return map(dto, EventConfigDto.class);
    }

    public List<EventConfigDto> mapToEventConfigDtoList(List<EventConfig> entities) {
        return entities.stream()
                .map(this::mapToEventConfigDto)
                .collect(Collectors.toList());
    }

    public Page<EventConfigDto> mapToEventConfigDtoPage(Page<EventConfig> page) {
        return page.map(this::mapToEventConfigDto);
    }
}
