package com.Unicash.issuehandling.security.other;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepo extends  JpaRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
}
