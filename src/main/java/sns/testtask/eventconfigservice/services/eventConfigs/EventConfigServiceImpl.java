package sns.testtask.eventconfigservice.services.eventConfigs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sns.testtask.eventconfigservice.domain.EventConfig;
import sns.testtask.eventconfigservice.domain.filters.EventConfigFilter;
import sns.testtask.eventconfigservice.exceptions.DuplicateEventConfigException;
import sns.testtask.eventconfigservice.exceptions.ResourceNotFoundException;
import sns.testtask.eventconfigservice.repositories.EventConfigRepository;
import sns.testtask.eventconfigservice.services.EventConfigService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventConfigServiceImpl implements EventConfigService {
    private final EventConfigRepository eventConfigRepository;

    @Override
    @Transactional
    public EventConfig createEventConfig(EventConfig config) {
        eventConfigRepository.findById(config.getId())
                .ifPresent(eventConfig -> {
                    throw new DuplicateEventConfigException(
                            String.format("Event config with id=%s is already exist", config.getId()));
        });
        return eventConfigRepository.save(config);
    }

    @Override
    @Transactional
    public EventConfig updateEventConfig(String id, EventConfig config) {
        if (eventConfigRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format("Event config with id=%s doesn't exist", config.getId()), id);
        }
        config.setId(id);
        return eventConfigRepository.update(config);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventConfig> paginateEventConfig(EventConfigFilter filter, Pageable page) {
        return eventConfigRepository.find(filter, page).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public EventConfig getEventConfigById(String id) {
        return eventConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("EventConfig with id='%s' not found", id), id));    }

    @Override
    @Transactional(readOnly = true)
    public List<EventConfig> findAllEventConfigs() {
        return eventConfigRepository.findAll();
    }
}
