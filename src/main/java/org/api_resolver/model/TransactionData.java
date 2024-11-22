package org.api_resolver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionData
{
    private CustomerInfo customerInfo;
    private String updated_at;
    private String created_at;
    private Fx fx;
    private Acquirer acquirer;
    private Transaction transaction;
    private boolean refundable;
    private Merchant merchant;
    private Ipn ipn;

}