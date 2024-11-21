package org.api_resolver.service.impl;

import lombok.RequiredArgsConstructor;
import org.api_resolver.bean.client.ClientGetBean;
import org.api_resolver.bean.login.LoginPostBean;
import org.api_resolver.bean.transaction.TransactionGetBean;
import org.api_resolver.bean.transactionQuery.TransactionQueryPostBean;
import org.api_resolver.bean.transactionReport.TransactionReportPostBean;
import org.api_resolver.dto.TransactionReportDTO;
import org.api_resolver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceImpl implements LoginService, ClientService, TransactionQueryService, TransactionReportService, TransactionService
{
    private final LoginPostBean loginPostBean;
    private final ClientGetBean clientGetBean;
    private final TransactionQueryPostBean transactionQueryPostBean;
    private final TransactionReportPostBean transactionReportPostBean;
    private final TransactionGetBean transactionGetBean;

    public String login()
    {
        return loginPostBean.login();
    }
    public String getClient(String transactionId)
    {
        return clientGetBean.getClient(transactionId);
    }
    public String transactionQuery()
    {
        return transactionQueryPostBean.transactionQuery();
    }
    public String transactionReport(TransactionReportDTO dto)
    {
        return transactionReportPostBean.transactionReport(dto);
    }
    public String getTransaction(String transactionId)
    {
        return transactionGetBean.getTransaction(transactionId);
    }


}
