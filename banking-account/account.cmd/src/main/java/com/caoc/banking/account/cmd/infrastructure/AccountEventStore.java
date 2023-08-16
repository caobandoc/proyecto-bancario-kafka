package com.caoc.banking.account.cmd.infrastructure;

import com.caoc.banking.account.cmd.domain.AccountAggregate;
import com.caoc.banking.account.cmd.domain.EventStoreRepository;
import com.caoc.banking.cqrs.core.events.BaseEvent;
import com.caoc.banking.cqrs.core.events.EventModel;
import com.caoc.banking.cqrs.core.exceptions.AggregationNotFoundException;
import com.caoc.banking.cqrs.core.exceptions.ConcurrencyException;
import com.caoc.banking.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
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
            var save = eventStoreRepository.save(eventModel);
            if (save != null) {
                // producir event para kafka
                System.out.println("Event saved: " + save.getEventType());
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
