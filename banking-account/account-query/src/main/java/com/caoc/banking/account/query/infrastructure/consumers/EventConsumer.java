package com.caoc.banking.account.query.infrastructure.consumers;

import com.caoc.banking.account.common.events.AccountClosedEvent;
import com.caoc.banking.account.common.events.AccountOpenedEvent;
import com.caoc.banking.account.common.events.FundsDepositedEvent;
import com.caoc.banking.account.common.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment acknowledgment);
    void consume(@Payload FundsDepositedEvent event, Acknowledgment acknowledgment);
    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment acknowledgment);
    void consume(@Payload AccountClosedEvent event, Acknowledgment acknowledgment);
}

