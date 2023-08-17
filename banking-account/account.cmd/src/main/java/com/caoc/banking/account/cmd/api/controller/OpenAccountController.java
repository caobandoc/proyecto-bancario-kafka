package com.caoc.banking.account.cmd.api.controller;

import com.caoc.banking.account.cmd.api.command.OpenAccountCommand;
import com.caoc.banking.account.cmd.api.dto.OpenAccountResponse;
import com.caoc.banking.account.common.dto.BaseResponse;
import com.caoc.banking.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/openAccount")
@RequiredArgsConstructor
@Slf4j
public class OpenAccountController {
    private final CommandDispatcher commonDispatcher;
    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try{
            commonDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("La cuenta se ha creado exitosamente", id), HttpStatus.CREATED);
        }catch (IllegalStateException e){
            log.warn(MessageFormat.format("No se pudo crear la cuenta {0}", id), e.toString());
            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            var safeErrorMessage = MessageFormat.format("Errores mientras procesaba el request {0}", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new OpenAccountResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
