package org.api_resolver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Acquirer
{
    private int id;
    private String name;
    private String code;
    private String type;
}