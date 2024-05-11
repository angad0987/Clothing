package com.example.clothing.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.clothing.Jwt.JwtConfig.JwtAuthenticationEntryPoint;
import com.example.clothing.Jwt.JwtConfig.JwtAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler2() {
        return new CustomAuthenticationOAuth2SuccessHandler();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**");
    }

    @Autowired
    private JwtAuthenticationEntryPoint point;

    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private CustomLogOutHandler logOutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/checkout", "/forgotPassword/verifyAccount",
                                "/forgotPassword/sendOTP", "/forgotPassword/verifyOTP",
                                "/forgotPassword/changePassword").authenticated()
                                .requestMatchers("/**").permitAll())
                .oauth2Login(oauth2 -> {
                    oauth2
                            .loginPage("/login")
                            .successHandler(authenticationSuccessHandler2());
                })
                .exceptionHandling(e -> e.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(l -> l.logoutUrl("/logout")
                        .addLogoutHandler(logOutHandler)
                        .logoutSuccessUrl("/"));

        http.addFilterBefore(filter,
                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
