package com.example.clothing.Interceptors;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("I am in pre handle filter method");
        String jwtToken = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            List<Cookie> cookiesList = Arrays.asList(cookies);
            if (cookies != null && cookies.length != 0) {
                Optional<String> jwtTokenOptional = cookiesList.stream()
                        .filter(cookie -> cookie.getName().equals("jwtToken"))
                        .map(Cookie::getValue)
                        .findFirst();
                if (jwtTokenOptional.isPresent()) {
                    jwtToken = jwtTokenOptional.get();
                }
            }

            // Set the JWT token in the request headers
            if (!jwtToken.isEmpty()) {
                System.out.println("JWT TOKEN IS ----------------->" + jwtToken);
                request.setAttribute("Authorization", "Bearer " + jwtToken);
            }
            System.out.println("there is no jwt token in the request");
            filterChain.doFilter(request, response);
        }

    }

}
