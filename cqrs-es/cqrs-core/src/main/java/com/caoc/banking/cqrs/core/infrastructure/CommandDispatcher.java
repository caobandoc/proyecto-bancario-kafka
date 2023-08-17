package com.caoc.banking.cqrs.core.infrastructure;

import com.caoc.banking.cqrs.core.commands.BaseCommand;
import com.caoc.banking.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
