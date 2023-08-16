package com.caoc.banking.account.cmd.api.command;

import com.caoc.banking.account.common.dto.AccountType;
import com.caoc.banking.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
