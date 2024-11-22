package org.api_resolver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Merchant
{
    private String name;
    private double originalAmount;
    private String originalCurrency;
    private double convertedAmount;
    private String convertedCurrency;
}