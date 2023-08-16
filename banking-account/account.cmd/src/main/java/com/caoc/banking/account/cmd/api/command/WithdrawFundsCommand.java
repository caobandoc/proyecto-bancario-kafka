package com.caoc.banking.account.cmd.api.command;

import com.caoc.banking.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WithdrawFundsCommand extends BaseCommand {
    private double amount;
}
