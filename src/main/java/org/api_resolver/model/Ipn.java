package org.api_resolver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ipn
{
    private boolean sent;
    private Merchant merchant;
}