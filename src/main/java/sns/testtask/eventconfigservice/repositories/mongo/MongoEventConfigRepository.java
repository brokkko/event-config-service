package sns.testtask.eventconfigservice.repositories.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;
import sns.testtask.eventconfigservice.domain.EventConfig;
import sns.testtask.eventconfigservice.domain.filters.EventConfigFilter;
import sns.testtask.eventconfigservice.repositories.EventConfigRepository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@ConditionalOnProperty(prefix = "storage", name = "type", havingValue = "mongo", matchIfMissing = true)
@RequiredArgsConstructor
public class MongoEventConfigRepository implements EventConfigRepository {
    private final MongoTemplate mongo;

    @Override
    public EventConfig save(EventConfig cfg) {
        LocalDateTime now = LocalDateTime.now();
        cfg.setCreatedAt(Optional.ofNullable(cfg.getCreatedAt()).orElse(now));
        cfg.setUpdatedAt(now);
        return mongo.save(cfg);
    }

    @Override
    public Optional<EventConfig> findById(String id) {
        return Optional.ofNullable(mongo.findById(id, EventConfig.class));
    }

    @Override
    public EventConfig update(EventConfig cfg) {
        cfg.setUpdatedAt(LocalDateTime.now());
        return mongo.save(cfg);
    }

    @Override
    public Page<EventConfig> find(EventConfigFilter filter, Pageable p) {
        Query query = new Query().with(p);
        List<Criteria> criteria = new ArrayList<>();
        Optional.ofNullable(filter.getEventType()).ifPresent(v -> criteria.add(Criteria.where("eventType").is(v)));
        Optional.ofNullable(filter.getSource()).ifPresent(v -> criteria.add(Criteria.where("source").is(v)));
        Optional.ofNullable(filter.getEnabled()).ifPresent(v -> criteria.add(Criteria.where("enabled").is(v)));
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        List<EventConfig> list = mongo.find(query, EventConfig.class);
        long total = mongo.count(query.skip(0).limit(0), EventConfig.class);
        return new PageImpl<>(list, p, total);
    }

    @Override
    public List<EventConfig> findAll() {
        return mongo.findAll(EventConfig.class);
    }
}

