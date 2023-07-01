package com.Unicash.issuehandling.security.other;

import com.Unicash.issuehandling.dto.request.auth.RefreshTokenIn;
import com.Unicash.issuehandling.security.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.Unicash.issuehandling.security.Util.getRole;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserInfoUserDetailsService userInfoUserDetailsService;
    private final AuthenticationProvider authenticationProvider;

    @Value("${myapp.secret.key}")
//weAreAbleToGetThemFrom application.properties b/c they are spring managed(have one of spring's annotation)
    private String jwtSecretKey;
    @Value("${myapp.bearer.key}")//
    private String jwtBearerKey;

    @PostMapping("/register")
    public void register(@Nullable @RequestParam("uname") String uname, @RequestParam("pass") String pass, @RequestParam("role") String role, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final UserInfo save;
        try {
            RoleEnum userRole = getRole(role);
            save = userInfoUserDetailsService.saveUser(new UserInfo(null, uname, "09xx", uname + "@me.com", pass, userRole));
            response.setHeader("access_token", Util.generateToken(save.getUsername(), request.getRequestURL().toString(), List.of(userRole.getRole())));
            response.setHeader("refresh_token", Util.generateRefreshToken(save.getUsername(), request.getRequestURL().toString()));
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error_message", e.getMessage());
            response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
//            return new UserInfo(save.getId(), save.getUserName(), save.getPhone(), save.getEmail(), save.getPassword(), save.getRole());
        }
    }

    @GetMapping("/getin")
    public void login(@RequestParam("uname") String uname, @RequestParam("pass") String pass, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final String username = request.getHeader("username");
//        final String password = request.getHeader("username");
        Authentication auth = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(uname, pass));
        if (auth != null) {
            User user = (User) auth.getPrincipal();
            List<String> claims = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            response.setHeader("access_token", Util.generateToken(user.getUsername(), request.getRequestURL().toString(), claims));
            response.setHeader("refresh_token", Util.generateRefreshToken(user.getUsername(), request.getRequestURL().toString()));
        } else {
            response.setHeader("error", "Incorrect Username and/or Password");
            response.setStatus(FORBIDDEN.value());

        }
    }

    //rememeber: add "Bearer " beforeThisString
    @GetMapping("/token/refresh")
    public void refreshToken(RefreshTokenIn refreshTokenIn, HttpServletRequest request, HttpServletResponse response) {
//        String authHeader = request.getHeader(AUTHORIZATION);
//        if(authHeader!=null && authHeader.startsWith((jwtBearerKey+" "))) {
//        String token = refreshTokenIn.getRefreshToken();
        if (refreshTokenIn.getRefreshToken() != null && refreshTokenIn.getRefreshToken().startsWith(jwtBearerKey + " "))
            try {
                String refresh_token = refreshTokenIn.getRefreshToken().substring((jwtBearerKey + " ").length());
                String username = Util.verifyJwt(refresh_token);
                UserInfo user = userInfoUserDetailsService.getUser(username);
                String access_token = Util.generateToken(username, request.getRequestURL().toString(), List.of(user.getRole().getRole()));

                response.setHeader("access_token", access_token);
                response.setHeader("refresh_token", refresh_token);

            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
            }
        else
            throw new RuntimeException("(Refresh Token is missing");
    }

}

//    @GetMapping("x")
//    public void x(@RequestParam("uname") String uname, @RequestParam("pass") String pass, HttpServletRequest request, HttpServletResponse response){
//        //        final String username = request.getHeader("username");
////        final String password = request.getHeader("username");
//        Authentication auth = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(uname, pass));
//        if (auth != null) {
//            User user = (User) auth.getPrincipal();
//            List<String> claims = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//            response.setHeader("access_token", Util.generateToken(user.getUsername(), request.getRequestURL().toString(), claims));
//            response.setHeader("refresh_token", Util.generateRefreshToken(user.getUsername(), request.getRequestURL().toString()));
//        }
//        else {
//            response.setHeader("error", "Incorrect Username and/or Password");
//            response.setStatus(FORBIDDEN.value());
//
//        }
//    }

