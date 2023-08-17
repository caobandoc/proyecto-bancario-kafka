package com.caoc.banking.cqrs.core.handler;

import com.caoc.banking.cqrs.core.domain.AggregateRoot;

public interface EventsSourcingHandler<T> {
    void save(AggregateRoot aggregate);
    T getById(String id);
}
