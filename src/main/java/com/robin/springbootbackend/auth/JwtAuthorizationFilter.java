package com.robin.springbootbackend.auth;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.robin.springbootbackend.account.Account;
import com.robin.springbootbackend.account.AccountService;
import com.robin.springbootbackend.enums.Role;
import com.robin.springbootbackend.role.RoleService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER_STRING = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    private final String ROLE_PREFIX = "ROLE_";

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    public JwtAuthorizationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        String token = header.replace("Bearer ", "");
        Jwt jwt = this.jwtService.decodeJWT(token);
        if (jwt == null){ return; }

        User currentAccount = (User) this.userDetailsService.loadUserByUsername(jwt.getSubject());
        String role = currentAccount.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.joining());

        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));

        Authentication authentication = new JwtAuthenticationToken(jwt,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
}
