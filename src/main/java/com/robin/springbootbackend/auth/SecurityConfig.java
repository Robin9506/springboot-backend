package com.robin.springbootbackend.auth;

import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.robin.springbootbackend.helper.LogRepository;
import com.robin.springbootbackend.helper.LogService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.UUID;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
    private final RsaKeyProperties rsaKeys;

    private final UserRepository userRepository;

    private final LogRepository logRepository;

    public SecurityConfig(RsaKeyProperties rsaKeys, UserRepository userRepository, LogRepository logRepository){
        this.rsaKeys = rsaKeys;
        this.userRepository = userRepository;
        this.logRepository = logRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http = http.csrf().disable();
        http = http
                .cors().and()
                .addFilterBefore(new JwtAuthorizationFilter(new JwtService(this.jwtEncoder(), this.jwtDecoder()), userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()

                .requestMatchers("/api/v1/product").permitAll()
                .requestMatchers("/api/v1/product/{id}").permitAll()
                .requestMatchers("/api/v1/product/ordered/{all}").permitAll()
                .requestMatchers("/api/v1/account").permitAll()
                .requestMatchers("/api/v1/promo").permitAll()
                .requestMatchers("/api/v1/order").permitAll()
                .requestMatchers((HttpMethod.POST),"/api/v1/auth").permitAll()
                .requestMatchers("/api/v1/cart").permitAll()
                .anyRequest().authenticated().and();


        http = http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() throws UnsupportedEncodingException {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();

    }

    @Bean
    JwtEncoder jwtEncoder() throws ParseException, UnsupportedEncodingException, NoSuchAlgorithmException {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey())
                  .build();
        JWKSource<SecurityContext> source = new ImmutableJWKSet<SecurityContext>(new JWKSet(jwk));
        return new NimbusJwtEncoder(source);
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return accountId -> (UserDetails) userRepository.findById(UUID.fromString(accountId))
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    @Bean
    public LogService customLogService(){
        return new LogService(this.logRepository);
    }
}
