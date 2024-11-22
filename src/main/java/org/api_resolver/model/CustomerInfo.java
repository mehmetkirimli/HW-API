package org.api_resolver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class CustomerInfo
{
    private String number;
    private String email;
    private String billingFirstName;
    private String billingLastName;
}