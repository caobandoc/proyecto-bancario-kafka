package com.caoc.banking.account.cmd.infrastructure;

import com.caoc.banking.account.cmd.domain.AccountAggregate;
import com.caoc.banking.cqrs.core.domain.AggregateRoot;
import com.caoc.banking.cqrs.core.events.BaseEvent;
import com.caoc.banking.cqrs.core.handler.EventsSourcingHandler;
import com.caoc.banking.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventsSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;
    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvent(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEventsForAggregate(id);
        if(events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }

        return aggregate;
    }
}
