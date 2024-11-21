package org.api_resolver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReportDTO
{
    private Date fromDate;
    private Date toDate;
    private Integer merchant;
    private Integer acquirer;
}
