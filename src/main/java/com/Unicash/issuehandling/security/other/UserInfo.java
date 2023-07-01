package com.Unicash.issuehandling.security.other;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class UserInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;
    private String phone;
    private String email;
    private String password;
    private RoleEnum role;
//    private Collection<Role> Roles = new ArrayList<>();
}

