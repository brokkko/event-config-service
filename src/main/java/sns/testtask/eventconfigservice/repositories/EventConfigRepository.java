package sns.testtask.eventconfigservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sns.testtask.eventconfigservice.domain.EventConfig;
import sns.testtask.eventconfigservice.domain.filters.EventConfigFilter;
import sns.testtask.eventconfigservice.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link EventConfig} entities.
 * Provides basic CRUD operations and filtering support.
 */
public interface EventConfigRepository {
    /**
     * Save a new EventConfig.
     *
     * @param cfg the EventConfig object to save
     * @return the saved EventConfig object with generated ID and timestamps
     */
    EventConfig save(EventConfig cfg);

    /**
     * Find an EventConfig by its identifier.
     *
     * @param id the ID of the EventConfig to find
     * @return an Optional containing the EventConfig if found, or empty otherwise
     */
    Optional<EventConfig> findById(String id);

    /**
     * Update an existing EventConfig.
     *
     * @param cfg the EventConfig object with updated values
     * @return the updated EventConfig object
     * @throws ResourceNotFoundException if the EventConfig does not exist
     */
    EventConfig update(EventConfig cfg);

    /**
     * Find and paginate EventConfig entries based on filter criteria.
     *
     * @param filter filter object containing eventType, source, and enabled criteria
     * @param pg the pagination parameters (page number, size, sort)
     * @return a Page of EventConfig matching the filter criteria
     */
    Page<EventConfig> find(EventConfigFilter filter, Pageable pg);

    /**
     * Retrieve all EventConfig entries.
     *
     * @return a List of all EventConfig objects
     */
    List<EventConfig> findAll();
}
