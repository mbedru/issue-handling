package com.Unicash.issuehandling.security;

import com.Unicash.issuehandling.security.other.RoleEnum;
import com.Unicash.issuehandling.security.other.UserInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    private final static String jwtSecretKey = "MYSecRet" ;

    public static String generateToken(String username, String requestURL, List<String> claims) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey.getBytes());
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 60*1000 * 30 )) // 15mins
                .withIssuer(requestURL)
                .withClaim("Roles", claims)
                .sign(algorithm);
    }

    public static String generateRefreshToken(String username, String requestURL) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey.getBytes());
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 60*1000 * 100 )) // 100mins
                .withIssuer(requestURL)
                .sign(algorithm);
    }

    public static String verifyJwt(String refresh_token) {
    // verify JWT and return username if successful.
        Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refresh_token);
        String username = decodedJWT.getSubject();
        return username;
    }

    public static RoleEnum getRole(String role) {
        RoleEnum userRole = role.equalsIgnoreCase(RoleEnum.DEVELOPER.getRole()) ? RoleEnum.DEVELOPER
                : role.equalsIgnoreCase(RoleEnum.SUPPORT.getRole()) ? RoleEnum.SUPPORT
                : role.equalsIgnoreCase(RoleEnum.ADMIN.getRole()) ? RoleEnum.ADMIN : null;
        if (userRole == null) throw new RuntimeException("Invalid Role passed!!!");
        return userRole;
    }
}
