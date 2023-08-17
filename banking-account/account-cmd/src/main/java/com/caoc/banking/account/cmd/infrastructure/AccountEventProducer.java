package com.caoc.banking.account.cmd.infrastructure;

import com.caoc.banking.cqrs.core.events.BaseEvent;
import com.caoc.banking.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventProducer implements EventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Override
    public void produce(String topic, BaseEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
