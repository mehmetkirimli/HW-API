package org.api_resolver.mapper;

import org.api_resolver.dto.TransactionDTO;
import org.api_resolver.dto.TransactionQueryDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionQueryDTOMapper
{
    public TransactionQueryDTO mapNoData(TransactionQueryDTO json)
    {
        TransactionQueryDTO dto = new TransactionQueryDTO();
        return dto;
    }

    public TransactionQueryDTO mapTwoData(TransactionQueryDTO json)
    {
        TransactionQueryDTO dto = new TransactionQueryDTO();
        dto.setFromDate(json.getFromDate());
        dto.setToDate(json.getToDate());
        return dto;
    }

    public TransactionQueryDTO mapThreeData(TransactionQueryDTO json)
    {
        TransactionQueryDTO dto = new TransactionQueryDTO();
        dto.setStatus(json.getStatus());
        dto.setOperation(json.getOperation());
        dto.setErrorCode(json.getErrorCode());
        return dto;
    }

    public TransactionQueryDTO mapFourData(TransactionQueryDTO json)
    {
        TransactionQueryDTO dto = new TransactionQueryDTO();
        dto.setFromDate(json.getFromDate());
        dto.setToDate(json.getToDate());
        dto.setMerchant(json.getMerchant());
        dto.setAcquirer(json.getAcquirer());
        return dto;
    }

    public TransactionQueryDTO mapTenData(TransactionQueryDTO json)
    {
        TransactionQueryDTO dto = new TransactionQueryDTO();
        dto.setFromDate(json.getFromDate());
        dto.setToDate(json.getToDate());
        dto.setMerchantId(json.getMerchantId());
        dto.setAcquirerId(json.getAcquirerId());
        dto.setStatus(json.getStatus());
        dto.setOperation(json.getOperation());
        dto.setPaymentMethod(json.getPaymentMethod());
        dto.setFilterField(json.getFilterField());
        dto.setFilterValue(json.getFilterValue());
        dto.setPage(json.getPage());
        return dto;
    }


}
