package com.Unicash.issuehandling.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SupportOut {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private List<String> enterprises;

}
////mon 13032023 17:24