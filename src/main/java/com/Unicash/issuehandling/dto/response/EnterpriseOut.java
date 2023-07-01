package com.Unicash.issuehandling.dto.response;

import com.Unicash.issuehandling.model.Module;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnterpriseOut {
    private Long id;
    private String name;
    private String module;
}
////mon 13032023 17:24