package com.example.clothing.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.clothing.Entities.Token;
import com.example.clothing.Entities.User;
import com.example.clothing.Services.ItemService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class MyFunctions {

    public static String get_User_Info_From_Cookies(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        String cookieValue = "";
        if (cookies != null) {
            List<Cookie> cookiesList = Arrays.asList(cookies);
            if (cookiesList != null && cookiesList.size() > 0) {
                Optional<String> cookieValueOptional = cookiesList.stream()
                        .filter(cookie -> cookie.getName().equals(cookieName))
                        .map(Cookie::getValue)
                        .findFirst();
                if (cookieValueOptional.isPresent()) {
                    System.out.println("Cookie value :" + cookieValueOptional.get());
                    return cookieValueOptional.get();
                }
            }
        }
        return null;

    }

}
