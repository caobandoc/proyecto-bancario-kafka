package com.caoc.banking.account.query.infrastructure.handlers;

import com.caoc.banking.account.common.events.AccountClosedEvent;
import com.caoc.banking.account.common.events.AccountOpenedEvent;
import com.caoc.banking.account.common.events.FundsDepositedEvent;
import com.caoc.banking.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
