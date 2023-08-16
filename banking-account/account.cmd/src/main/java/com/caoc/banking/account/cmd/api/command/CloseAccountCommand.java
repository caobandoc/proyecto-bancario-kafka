package com.caoc.banking.account.cmd.api.command;


import com.caoc.banking.cqrs.core.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
