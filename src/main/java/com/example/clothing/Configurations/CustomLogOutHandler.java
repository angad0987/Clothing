package com.example.clothing.Configurations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.example.clothing.Entities.Token;
import com.example.clothing.Services.ItemService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogOutHandler implements LogoutHandler {
    @Autowired
    private ItemService itemService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String requestHeader = (String) request.getAttribute("Authorization");

        System.out.println("Request Attribute  header is :" + requestHeader);
        String token = "";
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            System.out.println("I am in logout handler--------------------->");
            // looking good
            token = requestHeader.substring(7);
            System.out.println(token);
            Token tokenfromDatabase = this.itemService.findByToken(token).get();
            if (token != null) {
                tokenfromDatabase.setLoggedOut(true);
                this.itemService.saveToken(tokenfromDatabase);

                // remove all relevant cookies from the browser
                try {
                    this.removeAllCookies(request, response);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private void removeAllCookies(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Set the expiration time of relevant cookies to 0 to delete them
                if (cookie.getName().equals("jwtToken") || cookie.getName().equals("username")
                        || cookie.getName().equals("userid")) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/"); // Make sure to set the cookie path to match the one set during authentication
                    response.addCookie(cookie);
                }
            }
        }
    }

}
