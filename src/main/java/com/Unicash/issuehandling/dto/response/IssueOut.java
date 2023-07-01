package com.Unicash.issuehandling.dto.response;

import com.Unicash.issuehandling.model.*;
import com.Unicash.issuehandling.model.Module;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IssueOut {
    private Long id;
    private String description;
    private String enterprise;
    private String module;//is inside enterprise
    private String supportUserName;
    private String issueType;
    private String status;
}
////mon 13032023 17:24