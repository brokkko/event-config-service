package sns.testtask.eventconfigservice.repositories.elasticsearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import sns.testtask.eventconfigservice.domain.EventConfig;
import sns.testtask.eventconfigservice.domain.filters.EventConfigFilter;
import sns.testtask.eventconfigservice.repositories.EventConfigRepository;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(prefix = "storage", name = "type", havingValue = "elastic")
public class ElasticEventConfigRepository implements EventConfigRepository {
    private final ElasticsearchOperations es;
    private final String indexName;

    public ElasticEventConfigRepository(
            ElasticsearchOperations es,
            @Value("${elastic.index.event-config}") String indexName) {
        this.es = es;
        this.indexName = indexName;
    }

    @Override
    public EventConfig save(EventConfig cfg) {
        return es.save(cfg, IndexCoordinates.of(indexName));
    }

    @Override
    public Optional<EventConfig> findById(String id) {
        return Optional.ofNullable(es.get(id, EventConfig.class, IndexCoordinates.of(indexName)));
    }

    @Override
    public EventConfig update(EventConfig cfg) {
        return es.save(cfg, IndexCoordinates.of(indexName));
    }

    @Override
    public Page<EventConfig> find(EventConfigFilter filter, Pageable p) {

        Criteria criteria = new Criteria();
        if (filter.getEventType() != null) {
            criteria = criteria.and("eventType").is(filter.getEventType());
        }
        if (filter.getSource() != null) {
            criteria = criteria.and("source").is(filter.getSource());
        }
        if (filter.getEnabled() != null) {
            criteria = criteria.and("enabled").is(filter.getEnabled());
        }

        CriteriaQuery query = new CriteriaQuery(criteria)
                .setPageable(p);
        SearchHits<EventConfig> hits = es.search(query, EventConfig.class, IndexCoordinates.of(indexName));
        List<EventConfig> content = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return PageableExecutionUtils.getPage(content, p, hits::getTotalHits);
    }

    @Override
    public List<EventConfig> findAll() {
        return List.of();
    }
}
