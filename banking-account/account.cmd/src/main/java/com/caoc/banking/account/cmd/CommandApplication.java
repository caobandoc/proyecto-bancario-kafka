package com.caoc.banking.account.cmd;

import com.caoc.banking.account.cmd.api.command.CloseAccountCommand;
import com.caoc.banking.account.cmd.api.command.CommandHandler;
import com.caoc.banking.account.cmd.api.command.DepositFundsCommand;
import com.caoc.banking.account.cmd.api.command.OpenAccountCommand;
import com.caoc.banking.account.cmd.api.command.WithdrawFundsCommand;
import com.caoc.banking.cqrs.core.infrastructure.CommandDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CommandApplication {
	private final CommandDispatcher commandDispatcher;
	private final CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
	}

}
