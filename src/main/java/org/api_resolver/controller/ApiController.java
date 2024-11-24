package org.api_resolver.controller;

import lombok.RequiredArgsConstructor;
import org.api_resolver.dto.LoginDTO;
import org.api_resolver.dto.TransactionQueryDTO;
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

    @PostMapping(value = "/login")
    public String login(@RequestBody LoginDTO dto)
    {
        return loginService.login(dto);
    }
    @PostMapping(value = "/transactionQuery")
    public String transactionQuery(@RequestBody TransactionQueryDTO json)
    {
        return transactionQueryService.transactionQuery(json);
    }
    @GetMapping(value = "/getClient")
    public String getClient(@RequestParam ("transactionId") String  transactionId)
    {
        return clientService.getClient(transactionId);
    }
    @GetMapping(value = "/getTransaction")
    public String getTransaction(@RequestParam ("transactionId") String  transactionId)
    {
        return transactionService.getTransaction(transactionId);
    }
    @PostMapping(value = "/transactionReport")
    public String transactionReport(@RequestBody TransactionReportDTO dto)
    {
        return transactionReportService.transactionReport(dto);
    }
}
