package com.robin.springbootbackend.auth;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.robin.springbootbackend.account.AccountService;
import com.robin.springbootbackend.enums.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER_STRING = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    private final String ROLE_PREFIX = "ROLE_";

    private JwtService jwtService;

    public JwtAuthorizationFilter(JwtService jwtService) {
        this.jwtService = jwtService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String header = req.getHeader(HEADER_STRING);

        Role userRole = Role.USER;

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            System.out.println("null header");
            chain.doFilter(req, res);
            return;
        }

        String token = header.replace("Bearer ", "");
        Jwt jwt = this.jwtService.decodeJWT(token);


        userRole = userRole.castStringToRole(jwt.getClaim("role"));

        System.out.println(userRole);
        System.out.println(jwt.getClaims());
        System.out.println(token);
        System.out.println(auth + "-------------------------------------------------");
        chain.doFilter(req, res);
    }

    public List<GrantedAuthority> getAuthorizedRoles(Role userRole) {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + userRole));

        return list;
    }
}
