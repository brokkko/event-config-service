package sns.testtask.eventconfigservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sns.testtask.eventconfigservice.dtos.EventConfigDto;

import java.util.List;

import static sns.testtask.eventconfigservice.configs.ApiConstantsConfiguration.VALIDATION;

/**
 * REST‑controller for managing event changes
 */
@RequestMapping(value = VALIDATION)
public interface EventConfigController {

    /**
     * POST API operation that create event configuration.
     *
     * @param dto The event configuration object to create.
     * @return ResponseEntity with HTTP status indicating the
     * outcome of the operation.
     */
    @Operation(summary = "Create event configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event configuration created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "409", description = "Duplicate event configuration")
    })
    @PostMapping
    ResponseEntity<EventConfigDto> createEventConfig(@RequestBody @Valid EventConfigDto dto);

    /**
     * PUT API operation that update event configuration.
     *
     * @param id The id of event configuration.
     * @param dto The event configuration object to update.
     * @return ResponseEntity with HTTP status indicating the
     * outcome of the operation.
     */
    @Operation(summary = "Update event configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event configuration updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid event configuration data"),
            @ApiResponse(responseCode = "404", description = "Event configuration not found"),
    })
    @PutMapping("/{id}")
    ResponseEntity<EventConfigDto> updateEventConfig(@PathVariable String id,
                                 @RequestBody @Valid EventConfigDto dto);

    /**
     * GET API operation that find a list of filtered paginated EventConfigDto.
     *
     * @param eventType The event configuration eventType field filter.
     * @param source The event configuration source field filter.
     * @param enabled The event configuration enable field filter.
     * @param pageable Parameters object for pagination.
     * @return ResponseEntity with HTTP status indicating the
     * outcome of the operation.
     */
    @Operation(summary = "Get event configuration filtered list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event configuration found and filtered successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect filters parameters")
    })
    @GetMapping
    ResponseEntity<List<EventConfigDto>> findByFilter(@RequestParam(value = "eventType", required = false) String eventType,
                                                      @RequestParam(value = "source", required = false) String source,
                                                      @RequestParam(value = "enabled", required = false) Boolean enabled,
                                                      @ParameterObject @PageableDefault(page = 0, size = 20) Pageable pageable);
}
