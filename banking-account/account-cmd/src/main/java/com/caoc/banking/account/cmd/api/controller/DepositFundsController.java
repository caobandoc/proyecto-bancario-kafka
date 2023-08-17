package com.caoc.banking.account.cmd.api.controller;

import com.caoc.banking.account.cmd.api.command.DepositFundsCommand;
import com.caoc.banking.account.common.dto.BaseResponse;
import com.caoc.banking.cqrs.core.exceptions.AggregationNotFoundException;
import com.caoc.banking.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping("/api/v1/depositFunds")
@Slf4j
@RequiredArgsConstructor
public class DepositFundsController {

    private final CommandDispatcher commonDispatcher;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable String id, @RequestBody DepositFundsCommand command){
        try{
            command.setId(id);
            commonDispatcher.send(command);
            return ResponseEntity.ok().body(new BaseResponse("El deposito de dinero fue exitoso"));
        }catch (IllegalStateException | AggregationNotFoundException e){
            log.warn(MessageFormat.format("El cliente envio un request invalido {0}", e.toString()));
            return ResponseEntity.badRequest().body(new BaseResponse(e.getMessage()));
        }catch (Exception e){
            var safeErrorMessage = MessageFormat.format("Errores mientras procesaba el request {0}", id);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
