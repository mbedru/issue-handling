package com.Unicash.issuehandling.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupportIn {
    //for creating general/security User
    private Long userId;
    private String userName;//this one is for BOTH generalUser && Support
    private String phone;
    private String email;
    private String password;
    private String role ;
    //for creating Support
    private Long id;
    private String firstName;
    private String lastName;
    private List<Long> enterpriseIds = new ArrayList<>();
}
