package com.caoc.banking.account.query.infrastructure.handlers;

import com.caoc.banking.account.common.events.AccountClosedEvent;
import com.caoc.banking.account.common.events.AccountOpenedEvent;
import com.caoc.banking.account.common.events.FundsDepositedEvent;
import com.caoc.banking.account.common.events.FundsWithdrawnEvent;
import com.caoc.banking.account.query.domain.AccountRepository;
import com.caoc.banking.account.query.domain.BankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventHandler implements EventHandler{
    private final AccountRepository accountRepository;
    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .creationDate(event.getCreatedDate())
                .build();
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        bankAccount.ifPresent(ba -> {
            var currentBalance = ba.getBalance();
            var latestBalance = currentBalance + event.getAmount();
            ba.setBalance(latestBalance);
            accountRepository.save(ba);
        });

    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        bankAccount.ifPresent(ba -> {
            var currentBalance = ba.getBalance();
            var latestBalance = currentBalance - event.getAmount();
            ba.setBalance(latestBalance);
            accountRepository.save(ba);
        });
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
