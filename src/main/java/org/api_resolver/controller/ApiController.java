package org.api_resolver.controller;

import lombok.RequiredArgsConstructor;
import org.api_resolver.dto.TransactionDTO;
import org.api_resolver.dto.TransactionReportDTO;
import org.api_resolver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/transactionQuery")
    public String transactionQuery()
    {
        return transactionQueryService.transactionQuery();
    }
    @PostMapping(value = "/getClient")
    public String getClient(@RequestBody TransactionDTO dto)
    {
        return clientService.getClient(dto.getTransactionId());
    }
    @PostMapping(value = "/getTransaction")
    public String getTransaction(@RequestBody TransactionDTO dto) //Note : RequestParam ile de yapılabili
    {
        return transactionService.getTransaction(dto.getTransactionId());
    }
    @PostMapping(value = "/transactionReport")
    public String transactionReport(@RequestBody TransactionReportDTO dto)
    {
        return transactionReportService.transactionReport(dto);
    }
}
