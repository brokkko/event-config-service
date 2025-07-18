package sns.testtask.eventconfigservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sns.testtask.eventconfigservice.controllers.EventConfigController;
import sns.testtask.eventconfigservice.domain.EventConfig;
import sns.testtask.eventconfigservice.domain.filters.EventConfigFilter;
import sns.testtask.eventconfigservice.dtos.EventConfigDto;
import sns.testtask.eventconfigservice.services.EventConfigService;
import sns.testtask.eventconfigservice.services.eventConfigs.mapping.MappingEventConfigService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventConfigControllerImpl implements EventConfigController {

    private final EventConfigService eventConfigService;
    private final MappingEventConfigService mapper;

    @Override
    public ResponseEntity<EventConfigDto> createEventConfig(EventConfigDto dto) {
        var savedEventConfig = eventConfigService.createEventConfig(mapper.map(dto, EventConfig.class));
        return new ResponseEntity<>(mapper.map(savedEventConfig, EventConfigDto.class), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<EventConfigDto> updateEventConfig(String id, EventConfigDto dto) {
        var updatedEventConfig = eventConfigService.updateEventConfig(id, mapper.map(dto, EventConfig.class));
        return new ResponseEntity<>(mapper.map(updatedEventConfig, EventConfigDto.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EventConfigDto>> findByFilter(String eventType,
                                                             String source,
                                                             Boolean enabled,
                                                             Pageable pageable) {
        EventConfigFilter filter = EventConfigFilter.builder()
                .eventType(eventType)
                .source(source)
                .enabled(enabled)
                .build();
        var filteredEventConfigList = eventConfigService.paginateEventConfig(filter, pageable);
        return new ResponseEntity<>(mapper.mapToEventConfigDtoList(filteredEventConfigList), HttpStatus.OK);
    }
}
