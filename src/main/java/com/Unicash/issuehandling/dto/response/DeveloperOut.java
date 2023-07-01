package com.Unicash.issuehandling.dto.response;

import com.Unicash.issuehandling.model.Module;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeveloperOut {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String module;
}
////mon 13032023 17:24
