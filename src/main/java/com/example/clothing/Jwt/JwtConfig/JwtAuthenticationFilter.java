package com.example.clothing.Jwt.JwtConfig;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.clothing.Jwt.Helper.JwtHelper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // try {
        // Thread.sleep(500);
        // } catch (InterruptedException e) {
        // throw new RuntimeException(e);
        // }
        // Authorization
        System.out.println(" i am in do filter method ------------------->");

        String requestHeader = (String) request.getAttribute("Authorization");

        System.out.println("Request Attribute  header is :" + requestHeader);
        // Bearer 2352345235sdfrsfgsdfsdf
        logger.info(" Header :  {}", requestHeader);
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            // looking good
            token = requestHeader.substring(7);
            try {
                System.out.println("I AM IN TRY METHOD OF TOKEN VERIFICATION");
                username = this.jwtHelper.getUsernameFromToken(token);

            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }

        } else {
            logger.info("Invalid Header Value !! whoooooooooooooooo ");
        }

        //
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // fetch user detail from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {

                // set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String usernameReal = this.jwtHelper.getUsernameFromToken(token);
                System.out.println("Username real is " + usernameReal);
                // HttpSession session = request.getSession();
                // session.setAttribute("userid", username);
                // session.setAttribute("username", usernameReal);

            } else {
                logger.info("Validation fails !!");
            }

        }

        filterChain.doFilter(request, response);

    }
}
