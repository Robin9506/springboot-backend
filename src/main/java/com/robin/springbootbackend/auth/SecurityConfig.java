package com.robin.springbootbackend.auth;

import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.robin.springbootbackend.account.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
    private final RsaKeyProperties rsaKeys;

    public SecurityConfig(RsaKeyProperties rsaKeys){
        this.rsaKeys = rsaKeys;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http = http.cors().and().csrf().disable();

        http = http
                .addFilterBefore(new JwtAuthorizationFilter(new JwtService(this.jwtEncoder(), this.jwtDecoder())), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()

                .requestMatchers("/api/v1/product").permitAll()
                .requestMatchers("/api/v1/account").permitAll()
                .requestMatchers("/api/v1/promo").permitAll()
                .requestMatchers((HttpMethod.POST),"/api/v1/auth").permitAll()
                .anyRequest().authenticated()
                .and();


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



}
