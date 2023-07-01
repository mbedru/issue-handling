package com.Unicash.issuehandling.dto.request;

import com.Unicash.issuehandling.model.Module;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeveloperIn {
    //for creating general/security User
    private Long userId;
    private String userName;//this one is for BOTH generalUser && Developer
    private String phone;
    private String email;
    private String password;
    private String role ;
    //for creating Developer
    private Long devId;
    private String firstName;
    private String lastName;
    private Long moduleId;

}
