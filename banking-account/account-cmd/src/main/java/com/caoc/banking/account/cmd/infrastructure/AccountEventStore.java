package com.caoc.banking.account.cmd.infrastructure;

import com.caoc.banking.account.cmd.domain.AccountAggregate;
import com.caoc.banking.account.cmd.domain.EventStoreRepository;
import com.caoc.banking.cqrs.core.events.BaseEvent;
import com.caoc.banking.cqrs.core.events.EventModel;
import com.caoc.banking.cqrs.core.exceptions.AggregationNotFoundException;
import com.caoc.banking.cqrs.core.exceptions.ConcurrencyException;
import com.caoc.banking.cqrs.core.infrastructure.EventStore;
import com.caoc.banking.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;
    @Override
    public void saveEvent(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion)
            throw new ConcurrencyException();

        var version = expectedVersion;

        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }

        }

    }

    @Override
    public List<BaseEvent> getEventsForAggregate(String aggregateId) {
        List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty())
            throw new AggregationNotFoundException("La cuenta del banco es incorrecta");

        return eventStream.stream().map(EventModel::getEventData).toList();
    }
}
