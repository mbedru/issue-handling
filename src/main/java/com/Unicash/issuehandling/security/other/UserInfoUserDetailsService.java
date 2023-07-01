package com.Unicash.issuehandling.security.other;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthRepo authRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = authRepo.findByUsername(username);
        if (userInfo == null)
            throw new UsernameNotFoundException("UserInfo not found in the db.");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userInfo.getRole().getRole()));
        return new User( //we are just returning User(from UserDetails)
                userInfo.getUsername(),
                userInfo.getPassword(),
                authorities
        ); //returning spring-security-User expected by spring-security so that it can take this info and do(password comparison & check all authorities etc...)
    }
    public List<UserInfo> getUsers() {
//        log.info("Fetching all users from the DB");
        return authRepo.findAll();
    }

    public UserInfo getUser(String username) {
//        log.info("Getting user {} from the DB", username);
        return authRepo.findByUsername(username);
    }
    public UserInfo saveUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder
                .encode(userInfo.getPassword()));
        return authRepo.save(userInfo);
    }
}