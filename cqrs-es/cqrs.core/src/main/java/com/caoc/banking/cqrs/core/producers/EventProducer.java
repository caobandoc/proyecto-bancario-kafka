package com.caoc.banking.cqrs.core.producers;

import com.caoc.banking.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
