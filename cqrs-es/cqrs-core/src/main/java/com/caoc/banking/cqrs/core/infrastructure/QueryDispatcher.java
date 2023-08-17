package com.caoc.banking.cqrs.core.infrastructure;

import com.caoc.banking.cqrs.core.domain.BaseEntity;
import com.caoc.banking.cqrs.core.queries.BaseQuery;
import com.caoc.banking.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
