//package com.Unicash.issuehandling.security.filter;
//
//import com.Unicash.issuehandling.security.Util;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
////login part
//@RequiredArgsConstructor
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    @Value("${myapp.secret.key}")
//private String jwtSecretKey = "MYSecRet";
//    @Value("${myapp.bearer.key}")
//private String jwtBearerKey = "Bearer";
////    private final AuthenticationManager authenticationManager
//    private final AuthenticationProvider authenticationProvider;//if we want to make it general we can use AuthenticationManager
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        System.out.println("----- trying to authenticate");
//        String username =request.getParameter("username");
//        String password =request.getParameter("password");
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
//        System.out.println("----- trying to authenticate -2");
////        Authentication authenticate = null;
////        try {authenticate = authenticationProvider.authenticate(authenticationToken);}
////        catch(Exception e) {
////            System.out.println("EEEEEE-----"+e.getMessage());
////        }
////        if (authenticate == null)
////             System.out.println("authentication is null: "+ authenticate+" &*&*&* ");
////        else
////            System.out.println("authentication is not null: "+ authenticate+ "&*&*&** ");
//        return authenticationProvider.authenticate(authenticationToken);
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        User user = (User)authResult.getPrincipal();
//        Algorithm algorithm = Algorithm.HMAC256("MYSecRet".getBytes());
//        System.out.println("___successfulAuthentication++request.getRequestURL:___ "+ request.getRequestURL());
//
//        response.setHeader("access_token", Util.generateToken(user.getUsername(), request.getRequestURL().toString(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())));
//        response.setHeader("refresh_token", Util.generateRefreshToken(user.getUsername(), request.getRequestURL().toString()));
//
////        Map<String, String> tokens = new HashMap<>();
////        tokens.put("access_token", access_token);
////        tokens.put("refresh_token", refresh_token);
////        response.setContentType(APPLICATION_JSON_VALUE);
////        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//
//    }
//
//}
//
////writeValue method is used to serialize a Java object =>tokens
//// to JSON format and write it to an output stream => response.getOutputStream()
//
//
