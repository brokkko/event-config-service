package sns.testtask.eventconfigservice.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sns.testtask.eventconfigservice.domain.EventConfig;
import sns.testtask.eventconfigservice.domain.filters.EventConfigFilter;
import sns.testtask.eventconfigservice.exceptions.DuplicateEventConfigException;
import sns.testtask.eventconfigservice.exceptions.ResourceNotFoundException;

import java.util.List;

/**
 * Service interface for managing {@link EventConfig} entities.
 * Defines operations for creation, updating, retrieval, and pagination.
 */
public interface EventConfigService {

    /**
     * Create a new EventConfig.
     *
     * @param config the domain object representing the event configuration to create
     * @return the created EventConfig, including generated ID and timestamps
     * @throws DuplicateEventConfigException if a configuration with the same id already exists
     */
    EventConfig createEventConfig(EventConfig config);

    /**
     * Update an existing EventConfig by its identifier.
     *
     * @param id     the ID of the configuration to update
     * @param config the domain object containing updated fields
     * @return the updated EventConfig
     * @throws ResourceNotFoundException if no configuration with the given ID is found
     */
    EventConfig updateEventConfig(String id, EventConfig config);

    /**
     * Retrieve a paginated list of EventConfig entries matching the given filter criteria.
     *
     * @param filter the filter criteria (eventType, source, enabled)
     * @param page   the pagination information (page number, size, sort)
     * @return a {@link Page} of EventConfig objects matching the filter
     */
    List<EventConfig> paginateEventConfig(EventConfigFilter filter, Pageable page);

    /**
     * Retrieve a single EventConfig by its ID.
     *
     * @param id the identifier of the EventConfig to retrieve
     * @return the EventConfig with the specified ID
     * @throws ResourceNotFoundException if not found
     */
    EventConfig getEventConfigById(String id);

    /**
     * Retrieve all EventConfig entries without pagination.
     *
     * @return a list of all EventConfig objects
     */
    List<EventConfig> findAllEventConfigs();
}
