package com.caoc.banking.account.cmd.api.command;

import com.caoc.banking.account.cmd.domain.AccountAggregate;
import com.caoc.banking.cqrs.core.handler.EventsSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCommandHandler implements CommandHandler {

    private final EventsSourcingHandler<AccountAggregate> eventsSourcingHandler;

    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventsSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        var aggregate = eventsSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        eventsSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        var aggregate = eventsSourcingHandler.getById(command.getId());
        aggregate.withdrawFunds(command.getAmount());
        eventsSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = eventsSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventsSourcingHandler.save(aggregate);
    }
}
