package org.api_resolver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction
{
    private Merchant merchant;
    private String referenceNo;
    private String status;
    private String customData;
    private String type;
    private String operation;
    private String created_at;
    private String message;
    private String transactionId;
}