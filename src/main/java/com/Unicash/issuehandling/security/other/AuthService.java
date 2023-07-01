package com.Unicash.issuehandling.security.other;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private AuthRepo authRepo;
}
