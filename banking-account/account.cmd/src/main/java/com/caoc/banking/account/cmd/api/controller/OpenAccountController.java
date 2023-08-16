package com.caoc.banking.account.cmd.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/openAccount")
public class OpenAccountController {
    private final Logger logger = LoggerFactory.getLogger(OpenAccountController.class);
}
