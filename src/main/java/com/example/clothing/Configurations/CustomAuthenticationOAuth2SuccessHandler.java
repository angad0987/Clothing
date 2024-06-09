package com.example.clothing.Configurations;

import java.io.IOException;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.clothing.Entities.Token;
import com.example.clothing.Entities.User;
import com.example.clothing.Jwt.Helper.JwtHelper;
import com.example.clothing.Services.ItemService;
import com.example.clothing.Utilities.MyFunctions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomAuthenticationOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Value("${baseUrl}")
    private String baseUrl;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private ItemService itemService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
            String username = user.getAttribute("name");
            String email = user.getAttribute("email");

            System.out.println("uSERNAME IS : " + username);
            System.out.println("USER ID IS : " + email);

            // first we know this user is in database or not
            itemService.processOAuthPostLogin(email);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            System.out.println("User id is : " + userDetails.getUsername());

            String token = this.helper.generateToken(userDetails);
            System.out.println("Token generated is : " + token);
            User user1 = this.itemService.getUser(email);
            System.out.println("Username is : " + user1.getUserId());

            MyFunctions myFunctions = new MyFunctions();
            this.expireAllOldTokens(email);
            // token will be saved in database
            this.saveToken(token, user1);

            Cookie cookie = new Cookie("jwtToken", token);
            cookie.setPath("/"); // Set the cookie path to root so it's accessible from all paths
            cookie.setHttpOnly(true); // Set HttpOnly to true to prevent JavaScript access
            cookie.setMaxAge(3600); // Set the cookie expiration time in seconds (e.g., 1 hour)
            response.addCookie(cookie);

            Cookie cookie1 = new Cookie("username", username.replace(" ", "_"));
            cookie1.setPath("/"); // Set the cookie path to root so it's accessible from
            // all paths
            cookie1.setHttpOnly(true); // Set HttpOnly to true to prevent JavaScript
            // access
            cookie1.setMaxAge(3600); // Set the cookie expiration time in seconds (e.g.,
            // 1 hour)
            response.addCookie(cookie1);

            Cookie cookie2 = new Cookie("userid", user1.getUsername().replace(" ", "_"));
            cookie2.setPath("/"); // Set the cookie path to root so it's accessible from
            // all paths
            cookie2.setHttpOnly(true); // Set HttpOnly to true to prevent JavaScript
            // access
            cookie2.setMaxAge(3600); // Set the cookie expiration time in seconds (e.g.,
            // 1 hour)
            response.addCookie(cookie2);

            response.sendRedirect(baseUrl + "/");
        }

    }

    public void saveToken(String token, User user) {
        Token token1 = new Token();
        token1.setToken(token);
        token1.setLoggedOut(false);
        token1.setUser(user);
        this.itemService.saveToken(token1);

    }

    public void expireAllOldTokens(String user_id) {
        List<Token> tokens = itemService.getAllTokens(user_id);
        if (tokens != null && !tokens.isEmpty())
            tokens.forEach(t -> t.setLoggedOut(true));
        this.itemService.saveAllTokens(tokens);
    }
}
