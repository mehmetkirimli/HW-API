package org.api_resolver.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionList
{
    private int current_page;
    private List<TransactionData> data;
    private String first_page_url;
    private int from;
    private String next_page_url;
    private String path;
    private int per_page;
    private String prev_page_url;
    private int to;
}
