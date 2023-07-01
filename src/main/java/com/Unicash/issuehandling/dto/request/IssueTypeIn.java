package com.Unicash.issuehandling.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IssueTypeIn {

    private Long id;
    private String name;
    private String Description;

}
