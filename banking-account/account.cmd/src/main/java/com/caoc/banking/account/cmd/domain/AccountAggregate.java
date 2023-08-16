package com.caoc.banking.account.cmd.domain;

import com.caoc.banking.account.cmd.api.command.OpenAccountCommand;
import com.caoc.banking.account.common.events.AccountClosedEvent;
import com.caoc.banking.account.common.events.AccountOpenedEvent;
import com.caoc.banking.account.common.events.FundsDepositedEvent;
import com.caoc.banking.account.common.events.FundsWithdrawnEvent;
import com.caoc.banking.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private Double balance;

    public AccountAggregate(OpenAccountCommand command) {
        AccountOpenedEvent event = AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build();
        raiseEvent(event);
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount){
        if(Boolean.FALSE.equals(this.active)){
            throw new IllegalStateException("Los fondos no pueden ser depositados en una cuenta inactiva");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("La cantidad a depositar debe ser mayor a cero");
        }

        FundsDepositedEvent event = FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build();

        raiseEvent(event);
    }

    public void apply(FundsDepositedEvent event){
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount){
        if(Boolean.FALSE.equals(this.active)){
            throw new IllegalStateException("La cuenta bancaria esta inactiva");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("La cantidad a retirar debe ser mayor a cero");
        }
        if(amount > this.balance){
            throw new IllegalArgumentException("La cantidad a retirar debe ser menor o igual al balance de la cuenta");
        }
        FundsWithdrawnEvent event = FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build();

        raiseEvent(event);
    }

    public void apply(FundsWithdrawnEvent event){
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount(){
        if(Boolean.FALSE.equals(this.active)){
            throw new IllegalStateException("La cuenta bancaria ya esta inactiva");
        }
        AccountClosedEvent event = AccountClosedEvent.builder()
                .id(this.id)
                .build();
        raiseEvent(event);
    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }


}
