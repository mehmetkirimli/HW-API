package org.api_resolver.controller;

import lombok.RequiredArgsConstructor;
import org.api_resolver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api-resolver")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApiController
{
    private final LoginService loginService;
    private final ClientService clientService;
    private final TransactionQueryService transactionQueryService;
    private final TransactionReportService transactionReportService;
    private final TransactionService transactionService;

    @GetMapping(value = "/login")
    public String login()
    {
        return loginService.login();
    }

    @GetMapping(value = "/getClient")
    public String getClient()
    {
        return clientService.getClient();
    }


}
