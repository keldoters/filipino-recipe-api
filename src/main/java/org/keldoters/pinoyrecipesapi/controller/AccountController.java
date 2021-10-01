package org.keldoters.pinoyrecipesapi.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keldoters.pinoyrecipesapi.security.JwtUtility;
import org.keldoters.pinoyrecipesapi.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final JwtUtility jwtUtility;
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public AccountController(JwtUtility jwtUtility, MyUserDetailsService myUserDetailsService) {
        this.jwtUtility = jwtUtility;
        this.myUserDetailsService = myUserDetailsService;
    }

    @GetMapping("/token/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = jwtUtility.verifyToken(refreshToken);
                String email = decodedJWT.getSubject();
                User user = (User) myUserDetailsService.loadUserByUsername(email);
                String accessToken = jwtUtility.createAccessToken(user, request);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                new ObjectMapper().writeValue(response.getOutputStream(), Map.of("error", e.getMessage()));
            }
        } else {
            throw new RuntimeException("Missing refresh token");
        }

    }
}
