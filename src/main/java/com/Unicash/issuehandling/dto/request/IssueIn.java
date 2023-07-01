package com.Unicash.issuehandling.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IssueIn {
    private Long id;
    private String description;
    private Long enterpriseId;
    private Long supportId;
    private Long issueTypeId;
    private Long statusId;
}
