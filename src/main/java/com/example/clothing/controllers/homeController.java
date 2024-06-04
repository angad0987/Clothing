package com.example.clothing.controllers;

import com.example.clothing.Utilities.MyFunctions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.clothing.DTO.CartUpdateDTO;
import com.example.clothing.DTO.SignUpDTO;
import com.example.clothing.DTO.forgotPasswordDTO;
import com.example.clothing.Entities.Cart;
import com.example.clothing.Entities.ClothingItem;
import com.example.clothing.Entities.OTP;
import com.example.clothing.Entities.Order;
import com.example.clothing.Entities.PromoCode;
import com.example.clothing.Entities.Token;
import com.example.clothing.Entities.User;
import com.example.clothing.Entities.Wishlist;
import com.example.clothing.Jwt.Helper.JwtHelper;
import com.example.clothing.Jwt.jwtDTO.JwtRequest;
import com.example.clothing.Services.ItemService;
import com.example.clothing.Services.emailServices.EmailService;
import com.example.clothing.Utilities.MyFunctions;
import com.example.clothing.Utilities.PriceRange;
import com.example.clothing.oAuthConfig.Provider;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class homeController {
    @Value("${baseUrl}")
    private String baseUrl;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private ItemService itemService;

    private List<String> getItemTypes(String condition, String gender) {
        return itemService.getItemTypes(condition, gender);
    }

    private Map<String, List<ClothingItem>> getItemMap(String itemType, List<String> list) {
        Map<String, List<ClothingItem>> map = new HashMap<>();
        for (String s : list) {
            List<ClothingItem> items = itemService.getItemsByType(s);
            List<ClothingItem> selectedItems = items.stream().limit(3).collect(Collectors.toList());
            map.put(s, selectedItems);
        }
        System.out.println("Size of map is : " + map.size());

        return map;
    }

    private String getUpdatedCondition(String temp, String condition) {
        float temp1 = Float.parseFloat(temp);
        if (temp1 >= 24 && temp1 <= 45) {
            condition = condition + "_hot";

        } else if (temp1 >= 15 && temp1 < 24) {
            condition = condition + "_moderate";
        } else if (temp1 >= -5 && temp1 < 15) {
            condition = condition + "_cold";
        } else {
            condition = "coldWeather";
        }
        return condition;
    }

    @RequestMapping("/")
    public String home(HttpServletRequest request, HttpServletResponse response, Model m) {
        System.out.println("Base url is :--------------------------------------------------->" + baseUrl);
        m.addAttribute("baseUrl", baseUrl);
        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        return "index";
    }

    @RequestMapping("/ChooseDestination")
    public String destinationMethod(Model m, HttpServletRequest request) {
        System.out.println("Base url is :--------------------------------------------------->" + baseUrl);
        m.addAttribute("baseUrl", baseUrl);
        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        return "Destination";
    }

    @RequestMapping("/getPage")
    public String getPage(@RequestParam("conditionCode") String conditionCode, @RequestParam("gender") String gender,
            @RequestParam("temp") String temperature, Model m, HttpServletRequest request) {
        System.out.println(conditionCode);
        String condition = "";
        if (conditionCode.equals("1000")) {
            // condition="sunny";
            condition = getUpdatedCondition(temperature, "sunny");
            // System.out.println("Condition is sunny");..,
        }
        if (conditionCode.equals("1003")
                || conditionCode.equals("1006")) {
            // condition= "cloudy";
            condition = getUpdatedCondition(temperature, "cloudy");
        }
        if (conditionCode.equals("1009")) {
            // condition= "overcast";
            condition = getUpdatedCondition(temperature, "overcast");
        }
        if (conditionCode.equals("1030")) {
            // condition= "mist";
            condition = getUpdatedCondition(temperature, "mist");
        }
        if (conditionCode.equals("1063") || conditionCode.equals("1180") || conditionCode.equals("1069")
                || conditionCode.equals("1150") || conditionCode.equals("1153") || conditionCode.equals("1180")
                || conditionCode.equals("1183") || conditionCode.equals("1186") || conditionCode.equals("1189")
                || conditionCode.equals("1240") || conditionCode.equals("1273")) {
            // condition= "lightRain";
            condition = getUpdatedCondition(temperature, "lightRain");
        }
        if (conditionCode.equals("1072") || conditionCode.equals("1087") || conditionCode.equals("1147")
                || conditionCode.equals("1168") || conditionCode.equals("1171")) {
            condition = "coldWeather";
        }
        if (conditionCode.equals("1114") || conditionCode.equals("1117")) {
            condition = "extremeSnowDrizzle";
        }
        if (conditionCode.equals("1135")) {
            condition = "fog";
        }
        if (conditionCode.equals("1192") || conditionCode.equals("1195") || conditionCode.equals("1243")
                || conditionCode.equals("1246") || conditionCode.equals("1276")) {
            condition = "heavyRain";
        }
        if (conditionCode.equals("1201") || conditionCode.equals("1237") || conditionCode.equals("1198")
                || conditionCode.equals("1261") || conditionCode.equals("1264")) {
            condition = "heavyFrezzingRain";
        }
        if (conditionCode.equals("1204") || conditionCode.equals("1207") || conditionCode.equals("1249")) {
            condition = "sleet";
        }
        if (conditionCode.equals("1252")) {
            condition = "heavySleet";
        }
        if (conditionCode.equals("1210") || conditionCode.equals("1213") || conditionCode.equals("1216")
                || conditionCode.equals("1219") || conditionCode.equals("1255") || conditionCode.equals("1279")) {
            condition = "lightSnow";
        }
        if (conditionCode.equals("1225") || conditionCode.equals("1258") || conditionCode.equals("1282")) {
            condition = "HeavySnow";
        }

        // retriving item types related to condition
        System.out.println(condition);
        List<String> list = getItemTypes(condition, gender);
        for (String s : list) {
            System.out.println(s);
        }
        String returnGender = "";
        if (gender.equals("Male")) {
            returnGender = "Male";
        }
        if (gender.equals("Female")) {
            returnGender = "Female";
        }
        // m.addAttribute("gender", returnGender);
        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        m.addAttribute("itemTypes", list);
        Map<String, List<ClothingItem>> map = getItemMap(gender, list);
        m.addAttribute("itemsByType", map);
        return "Show_items";

    }

    @RequestMapping("/getTypes/{condition}/{gender}")
    public String getItemTypes(@PathVariable("condition") String condition, @PathVariable("gender") String gender,
            Model m, HttpServletRequest request) {
        System.out.println("Condition is : " + condition);
        System.out.println("Gender is : " + gender);
        List<String> list = getItemTypes(condition, gender);
        System.out.println("Size of list is " + list.size());
        m.addAttribute("itemTypes", list);

        // now we have to us a map to map a itemtype to its list of items
        Map<String, List<ClothingItem>> map = getItemMap(gender, list);
        m.addAttribute("itemsByType", map);
        // List<ClothingItem> list1 = map.get("Tshirts");
        // System.out.println("NO of tshirts are : " + list1.size());
        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        return "Show_items";
    }

    @RequestMapping("/showContent/{itemType}/{gender}")
    public String showContent(@PathVariable("itemType") String type, @PathVariable("gender") String gender, Model m) {
        List<ClothingItem> list = itemService.getItems(type, gender);
        m.addAttribute("items", list);
        m.addAttribute("itemType", type);
        return "show_Content";
    }

    // @RequestMapping("/loginUser")
    // public String loginMethod(@ModelAttribute("user") User user, BindingResult
    // result, Model model, HttpSession session) {
    ////
    //// String username = itemService.getUsername(user.getUserId(),
    // user.getPassword());
    //// if (username != null) {
    //// session.setAttribute("username", username);
    //// session.setAttribute("userid", user.getUserId());
    //// model.addAttribute("success1", "User login successfully!");
    //// return "index";
    //// } else {
    //// model.addAttribute("fail", "failed");
    //// return "login";
    //// }
    // return "index";
    // }
    //
    @RequestMapping("/logout")
    public void logoutMethod(HttpSession session, HttpServletResponse response) {

        // return "index";
    }

    // @RequestMapping("/loginUser") // Handle POST requests to
    // "/Clothing/loginUser"
    // public String loginUser(/* Add method parameters as needed */) {
    // // Handle the login user request
    // return "redirect:/"; // Redirect to the home page after login
    // }
    private void saveToken(String token, User user) {
        Token token1 = new Token();
        token1.setToken(token);
        token1.setLoggedOut(false);
        token1.setUser(user);
        this.itemService.saveToken(token1);

    }

    private void expireAllOldTokens(String user_id) {
        List<Token> tokens = itemService.getAllTokens(user_id);
        if (tokens != null && !tokens.isEmpty()) {
            tokens.forEach(t -> t.setLoggedOut(true));
            this.itemService.saveAllTokens(tokens);
        }

    }

    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("jwtRequest") JwtRequest jwtRequest, HttpServletResponse response,
            HttpSession session, Model m, RedirectAttributes redirectAttributes) {
        System.out.println("User id is : " + jwtRequest.getUserid());
        System.out.println("User password is : " + jwtRequest.getPassword());

        this.doAuthenticate(jwtRequest.getUserid(), jwtRequest.getPassword());
        // now user is authenticated successfully
        User user = this.itemService.getUser(jwtRequest.getUserid());
        this.handleJWT_Token_Generation_and_Adding_to_Cookies(jwtRequest.getUserid(), response);
        m.addAttribute("username", user.getUserId());

        // redirectAttributes.addFlashAttribute("username", user.getUsername());
        // session.setAttribute("username", user.getUsername());
        // session.setAttribute("userid", jwtRequest.getUserid());

        return "redirect:/";
        // return "index";
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
                password);
        try {
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println(" I am in catch method");
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(BadCredentialsException e, RedirectAttributes redirectAttributes,
            HttpServletRequest request) throws Exception {
        System.out.println("Invalid Username or Password");
        redirectAttributes.addFlashAttribute("errorMessage", "Invalid Credentials");
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/newUser")
    public String SignUp(Model m, HttpServletRequest request) throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO();
        m.addAttribute("signupData", signUpDTO);
        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        return "signup";
    }

    // @RequestMapping(path = "/handleSignup", method = RequestMethod.POST)
    // public String insertUser(@Valid @ModelAttribute("signupData") SignUpDTO
    // signupData, BindingResult results,
    // Model m, HttpServletRequest request) {
    // if (results.hasErrors()) {
    // System.out.println("there are errors !!!");
    // // m.addAttribute("signupData", signupData);
    // return "signup";
    // }

    // itemService.CreateNewUser(itemService.getUserFromSignUpDTO(signupData));

    // HttpSession session = request.getSession();
    // session.setAttribute("username", signupData.getUsername());
    // session.setAttribute("userid", signupData.getUserId());

    // return "index";
    // }

    @RequestMapping(value = "/handleSignup", method = RequestMethod.POST)
    public ResponseEntity<?> handleSignup(@Valid @RequestBody SignUpDTO signupData, BindingResult results,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        // First we have to verify email using user entered otp(one time password)
        // if otp is correct then we can create new user
        // if otp is incorrect then we can not create new user
        if (!this.itemService.validateOtp(signupData.getUserId(), signupData.getOtp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }

        System.out.println("I am in controller method");
        if (results.hasErrors()) {
            System.out.println(results.getFieldErrors());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(results.getFieldErrors());
        }
        try {
            itemService.CreateNewUser(itemService.getUserFromSignUpDTO(signupData));
            handleJWT_Token_Generation_and_Adding_to_Cookies(signupData.getUserId(), response);
            // we have to generate token and set to cookies as well

        } catch (Exception e) {
            System.out.println("Exception is : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
        return ResponseEntity.status(HttpStatus.OK).body("User created successfully");
    }

    @RequestMapping("/collection")
    public String getCollectionsPage(Model m, HttpServletRequest request) {
        m.addAttribute("content", "notVisible");
        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        return "Collections";
    }

    @RequestMapping("/collections/{weather}")
    public String getCollectionsItems(@PathVariable("weather") String weather, Model m, HttpServletRequest request) {
        System.out.println("I am in controller method");
        System.out.println(weather);

        List<String> list = itemService.getCollectionTypes(weather);
        m.addAttribute("content", "visible");
        m.addAttribute("types", list);
        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        return "Collections";
    }

    @RequestMapping("/CheckWeather")
    public String getWeatherPage(HttpServletRequest request, Model m) {
        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        return "Weather";
    }

    @RequestMapping("/Cart")
    public String CartMethod(HttpSession session, Model m, HttpServletRequest request) {
        System.out.println("USERNAME FROM COOKIES ISS ::::::----------->"
                + MyFunctions.get_User_Info_From_Cookies(request, "username"));

        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        m.addAttribute("userid", MyFunctions.get_User_Info_From_Cookies(request, "userid"));
        String customer_id = MyFunctions.get_User_Info_From_Cookies(request, "userid");
        if (customer_id == null || customer_id.equals("")) {

            return "Cart";

        }
        List<Object[]> list = itemService.getCartItems(customer_id);
        int listLength = list.size();
        m.addAttribute("size", listLength);
        m.addAttribute("cartList", list);
        return "Cart";
    }

    @RequestMapping(path = "/addtocart", method = RequestMethod.POST)
    public String AddToCartMethod(@RequestParam("item_id") String item_id, @RequestParam("quan") int quan,
            RedirectAttributes redirectAttributes, HttpSession session, Model m, HttpServletRequest request) {
        String customer_name = MyFunctions.get_User_Info_From_Cookies(request, "username");

        String customer_id = MyFunctions.get_User_Info_From_Cookies(request, "userid");
        System.out.println(customer_name);
        System.out.println(customer_id);
        if (customer_name == null || customer_name.equals("")) {
            m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
            m.addAttribute("loginMessage", "Log in to your account to add products to Cart!!");
            return "index";

        }

        itemService.addToCart(item_id, customer_id, quan);
        String referer = request.getHeader("Referer");
        // m.addAttribute("itemAdded","Item added Successfully");

        // this model object is not used when we redirect to another page becuase in
        // redirect browser send new request so
        // we have to use flash attribute, we use object of RedirectAttribute
        redirectAttributes.addFlashAttribute("itemAdded", "Item added Successfully");
        redirectAttributes.addFlashAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));

        return "redirect:" + referer;
    }

    @RequestMapping("deleteFromCart/{id}")
    public String deleteItemFromCart(@PathVariable("id") int id, HttpSession session,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String customer_id = MyFunctions.get_User_Info_From_Cookies(request, "userid");
        itemService.RemoveFromCart(id, customer_id);
        redirectAttributes.addFlashAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        redirectAttributes.addFlashAttribute("userid", MyFunctions.get_User_Info_From_Cookies(request, "userid"));
        return "redirect:/Cart";
    }

    @GetMapping("/wishlist")
    public String getWishlist(HttpSession session, Model m, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(" I am in the wishlist method");
        System.out.println("USERNAME FROM COOKIES ISS ::::::----------->"
                + MyFunctions.get_User_Info_From_Cookies(request, "username"));

        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        m.addAttribute("userid", MyFunctions.get_User_Info_From_Cookies(request, "userid"));

        String user_id = MyFunctions.get_User_Info_From_Cookies(request, "userid");
        if (user_id == null || user_id.equals("")) {

            return "Wishlist";

        }
        //
        // wishlist items of user id
        List<Wishlist> list = itemService.getWishlistItems(user_id);

        // types of items in the wishlist
        List<Wishlist> relatedItems = itemService.getWishlistItemsTypes(user_id);

        int size = list.size();
        int size1 = relatedItems.size();
        System.out.println("No of related products" + size1);
        // m.addAttribute("Wishlist", list);
        m.addAttribute("size", size);
        m.addAttribute("relatedItems", relatedItems);
        return "Wishlist";
    }

    @RequestMapping(path = "/AddToWishlist", method = RequestMethod.POST)
    // @ResponseBody is an annotation in Spring MVC framework for a method that
    // returns data directly to the client instead of relying on a view resolver to
    // render the data into a view such as HTML.
    @ResponseBody
    public String addInWishList(
            @RequestParam("itemid") String itemid,
            @RequestParam("itemname") String itemName,
            @RequestParam("itemprice") String itemPrice,
            @RequestParam("quantity") String quantity,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("user_id") String userId) {

        Wishlist wishlistItem = new Wishlist();

        // eh itemprice te userid set ni ho rhian c fr apa ehna nu explicitly set kita
        int itemid1 = Integer.parseInt(itemid);
        float itemprice = Float.parseFloat(itemPrice);
        int parsed_quantity = Integer.parseInt(quantity);
        wishlistItem.setItem_id(itemid1);
        wishlistItem.setItemprice(itemprice);
        wishlistItem.setItemname(itemName);
        wishlistItem.setCategory(category);
        wishlistItem.setDescription(description);
        wishlistItem.setUserid(userId);
        wishlistItem.setQuantity(parsed_quantity);

        System.out.println(wishlistItem.getItem_id());

        // adding in wishlist table
        itemService.AddtoWishlist(wishlistItem);
        return "Item added successfully in wishlist";

    }

    @RequestMapping(path = "/CheckWishlist/{userid:.+}", method = RequestMethod.GET)
    // @ResponseBody
    public ResponseEntity<List<Integer>> checkWishlist(@PathVariable("userid") String userid,
            HttpServletRequest request) {
        System.out.println("I am in controller method " + userid);
        List<Integer> list = itemService.getWishlistItemsIds(userid);
        System.out.println(list.size());
        list.forEach(item -> System.out.println(item));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "/deleteFromWishlist/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> deleteItemFromWislist(@PathVariable("id") int id, HttpSession session,
            HttpServletRequest request) {
        System.out.println("USERNAME FROM COOKIES ISS ::::::----------->"
                + MyFunctions.get_User_Info_From_Cookies(request, "username"));
        String customer_id = MyFunctions.get_User_Info_From_Cookies(request, "userid");
        String result = itemService.RemoveFromWishlist(id, customer_id);
        List<String> list = new ArrayList<>();
        list.add(result);
        System.out.println(1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
        // return "redirect:/wishlist";
    }

    @RequestMapping(path = "/updatedWishlist", method = RequestMethod.GET)
    public ResponseEntity<List<List<Wishlist>>> getUpdatedWishlist(HttpSession session, HttpServletRequest request) {
        System.out.println("USERNAME FROM COOKIES ISS ::::::----------->"
                + MyFunctions.get_User_Info_From_Cookies(request, "username"));

        String user_id = MyFunctions.get_User_Info_From_Cookies(request, "userid");

        // wishlist items of user id
        List<Wishlist> list = itemService.getWishlistItems(user_id);

        // types of items in the wishlist
        List<Wishlist> relatedItems = itemService.getWishlistItemsTypes(user_id);

        // we return list which is sum of list of wishlist items and list of related
        // items
        List<List<Wishlist>> fullList = new ArrayList<>();
        fullList.add(list);
        fullList.add(relatedItems);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(fullList, headers, HttpStatus.OK);

    }

    @RequestMapping("/booking")
    public String booking() {
        return "Booking";
    }

    @RequestMapping("/singleItem/{itemid}")
    public String getSingleItemPage(@PathVariable("itemid") int id, Model m, HttpServletRequest request) {
        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        ClothingItem item = this.itemService.getSingleItem(id);
        m.addAttribute("item", item);
        return "SingleItem";
    }

    @RequestMapping("/checkout")
    public String getCheckoutPage(@RequestParam("total") String total, @RequestParam("taxOnAmount") String tax,
            HttpSession session, Model m,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String customer_id = MyFunctions.get_User_Info_From_Cookies(request, "userid");
        List<Object[]> list = itemService.getCartItems(customer_id);
        User user = itemService.getUser(customer_id);
        int listLength = list.size();
        session.setAttribute("total", total);
        session.setAttribute("size", listLength);
        session.setAttribute("cartList", list);
        session.setAttribute("user", user);
        session.setAttribute("taxAmount", tax);
        return "redirect:checkoutRedirect";
    }

    @RequestMapping("/checkoutRedirect")
    public String requestMethodName(HttpSession session, Model m, HttpServletRequest request,
            RedirectAttributes redirect) {
        String total = (String) session.getAttribute("total");
        int listLength = (int) session.getAttribute("size");
        List<Object[]> list = (List<Object[]>) session.getAttribute("cartList");
        User user = (User) session.getAttribute("user");
        String tax = (String) session.getAttribute("taxAmount");

        // Set attributes in model
        m.addAttribute("total", total);
        m.addAttribute("size", listLength);
        m.addAttribute("cartList", list);
        m.addAttribute("user", user);
        m.addAttribute("tax", tax);
        return "CheckOut_Page";
    }

    @RequestMapping("/login")
    public String getLoginPage(HttpServletRequest request, Model m) {
        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        return "login";
    }

    @RequestMapping(path = "/handleFilterForm", method = RequestMethod.GET)
    public String handlefilterForm(@RequestParam(value = "gender", required = true) String gender,
            @RequestParam(value = "product", required = true) String productType,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "discount", required = false) String discount,
            @RequestParam(value = "color", required = false) String color, Model m, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        System.out.println("I am in handlefilter form method");
        System.out.println("New color is : " + color);
        redirectAttributes.addFlashAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));

        // now we have 8 cases
        // 1. when user select only gender and itemType
        // 2 . when user select gender,itemType and price
        // 3. when user select gender,itemType and discount
        // 4. when user select gender,itemType and color
        // 5. when user select gender,itemType and price and discount
        // 6. when user select gender,itemType and price and color
        // 7. when user select gender,itemType and discount and color
        // 8. when user select gender,itemType and price and discount and color
        PriceRange range = new PriceRange();
        if (price != null) {
            range = PriceRange.getRange(price);
        }
        System.out.println(range);
        Float discount1 = 0f;
        if (discount != null) {
            discount1 = Float.parseFloat(discount);
        }
        System.out.println("COLOR IS : " + color);

        List<ClothingItem> items;

        // default case
        // case 1 when user selects only gender and productType
        if (price == null && discount == null && color == null) {
            items = itemService.getItemsByTypeAndGender(productType, gender);
        }
        // case 2 when user selects gender ,product type and price
        else if (discount == null && color == null) {
            items = itemService.getItemsByTypeAndGenderAndPriceRange(productType, gender, range.getMinRange(),
                    range.getMaxRange());
        }

        // case 3 when user selects gender ,product type and discount
        else if (price == null && color == null) {
            items = itemService.getItemsByTypeAndGenderAndDiscount(productType, gender, discount1);
        }
        // case 4 when user selects gender ,product type and color
        else if (price == null && discount == null) {
            items = itemService.getItemsByTypeAndGenderAndColor(productType, gender, color);
        }
        // case 5 when user selects doesnot select price
        else if (price == null) {
            items = itemService.getItemsByTypeAndGenderAndDiscountAndColor(productType, gender, discount1, color);
        }
        // case 6 when user selects doesnot select discount
        else if (discount == null) {
            items = itemService.getItemsByTypeAndGenderAndPriceRangeAndColor(productType, gender, range.getMinRange(),
                    range.getMaxRange(), color);
        }
        // case 7 when user selects doesnot select color
        else if (color == null) {
            items = itemService.getItemsByTypeAndGenderAndPriceRangeAndDiscount(productType, gender,
                    range.getMinRange(),
                    range.getMaxRange(), discount1);
        }
        // case 8 when user selects all things
        else {
            items = itemService.getItemsByTypeAndGenderAndPriceRangeAndDiscountAndColor(productType, gender,
                    range.getMinRange(), range.getMaxRange(), discount1, color);
        }

        int size = items.size();

        redirectAttributes.addFlashAttribute("size", size);
        System.out.println("Size of results is : " + size);

        String referer = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("items", items);
        return "redirect:" + referer;
    }

    @RequestMapping("/emptyCart/{userid}")
    public String emptyCart(@PathVariable("userid") String userid, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        try {
            itemService.emptyCart(userid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        redirectAttributes.addFlashAttribute("userid", MyFunctions.get_User_Info_From_Cookies(request, "userid"));
        return "redirect:/Cart";

    }

    @RequestMapping("/findIdPage")
    public String getForgotPage() {
        return "findIdPage";
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestBody String email) {
        System.out.println("Email is : " + email);
        // firstly we need to know that this user with this email already exists or not
        if (this.itemService.userAlreadyExist(email)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User with this email already exists");
        }

        // generate otp of 6 digits
        int otp = new Random().nextInt(99999) + 100000;
        this.itemService.saveOtpInDatabase(email, otp);
        System.out.println("OTP is : " + otp);
        boolean b = this.emailService.sendEmail(email, "One time password for authentication in ForecastFits.com",
                String.valueOf(otp));
        if (b) {
            return ResponseEntity.ok("otp sent successfully");
        }
        return ResponseEntity.badRequest()
                .body("Error in sending otp to your given email address, use another email address");
    }

    @PostMapping("/forgotPassword/sendOTP")
    public ResponseEntity<String> sendOtpTOEmail(@RequestBody String email) {
        System.out.println("email is " + email);
        // TODO: process POST request
        System.out.println("i AM IN SEND OTP METHOD");
        int otp = new Random().nextInt(99999) + 100000;
        this.itemService.saveOtpInDatabase(email, otp);
        System.out.println("OTP is : " + otp);
        boolean b = this.emailService.sendEmail(email, "One time password for authentication in ForecastFits.com",
                String.valueOf(otp));
        System.out.println(b);
        if (b) {
            System.out.println("sendin respoinse");
            return ResponseEntity.ok("otp sent successfully");
        }
        return ResponseEntity.badRequest()
                .body("Error in sending otp to your given email address, use another email address");
    }

    @PostMapping("/forgotPassword/verifyOTP")
    public String verifyOTP(Model m, HttpServletRequest request, @RequestParam("email") String email,
            @RequestParam("otp") Integer otp, RedirectAttributes redirectAttributes) {
        if (!this.itemService.validateOtp(email, otp)) {
            String referer = request.getHeader("Referer");
            redirectAttributes.addFlashAttribute("otp", "invalid");
            return "redirect:" + referer;
        }
        // if otp s are verified then we goes to forgotPassword page for generating new
        // password
        m.addAttribute("userid", email);
        return "forgotPassword";

    }

    private void handleJWT_Token_Generation_and_Adding_to_Cookies(String userId, HttpServletResponse response)
            throws IOException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        System.out.println("User id is : " + userDetails.getUsername());
        User user = this.itemService.getUser(userId);

        // token generation
        String token = this.helper.generateToken(userDetails);

        System.out.println("Token generated is : " + token);

        // expire all old tokens of user
        MyFunctions myFunctions = new MyFunctions();
        this.expireAllOldTokens(userId);
        // token will be saved in database
        this.saveToken(token, user);

        Cookie cookie = new Cookie("jwtToken", token);
        cookie.setPath("/"); // Set the cookie path to root so it's accessible from all paths
        cookie.setHttpOnly(true); // Set HttpOnly to true to prevent JavaScript access
        cookie.setMaxAge(3600); // Set the cookie expiration time in seconds (e.g., 1 hour)
        response.addCookie(cookie);

        System.out.println("Username is : " + user.getUserId());

        Cookie cookie1 = new Cookie("username", user.getUserId().replace(" ", "_"));
        cookie1.setPath("/"); // Set the cookie path to root so it's accessible from
        // all paths
        cookie1.setHttpOnly(true); // Set HttpOnly to true to prevent JavaScript
        // access
        cookie1.setMaxAge(3600); // Set the cookie expiration time in seconds (e.g.,
        // 1 hour)
        response.addCookie(cookie1);

        Cookie cookie2 = new Cookie("userid", user.getUsername().replace(" ", "_"));
        cookie2.setPath("/"); // Set the cookie path to root so it's accessible from
        // all paths
        cookie2.setHttpOnly(true); // Set HttpOnly to true to prevent JavaScript
        // access
        cookie2.setMaxAge(3600); // Set the cookie expiration time in seconds (e.g.,
        // 1 hour)
        response.addCookie(cookie2);
    }

    @RequestMapping("/conditions")
    public String getTermsandConditionsPage() {
        return "conditionsOfUse";
    }

    @PostMapping("/forgotPassword/checkUser")
    public ResponseEntity<?> checkUserInDatabase(@RequestBody String email, HttpServletResponse response) {
        System.out.println(" i am in check user method");
        // firstly we need to know that this user with this email already exists or not
        if (this.itemService.userAlreadyExist(email)) {
            User user = itemService.getUser(email);

            // user exists in database then generate jwt token for page access store in
            // cookies
            this.handleJWT_Token_Generation_and_Adding_to_Cookies(email, response);

            // response.sendRedirect("/accountF");
            return ResponseEntity.status(HttpStatus.OK).body(user.getUserId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this email not exists exists");
        }
    }

    @GetMapping("/forgotPassword/checkUser")
    public String getMethodName() {
        return "findIdPage";
    }

    @GetMapping("/forgotPassword/verifyAccount")
    public String getverifyAccountPage(@RequestParam("verifyEmail") String email, Model m) {
        System.out.println("Email is : " + email);
        m.addAttribute("email", email);
        return "verifyAccount";
    }

    @PostMapping("/forgotPassword/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody forgotPasswordDTO passwordDTO,
            BindingResult results,
            Model m) {
        System.out.println("User id is in --------------------------->" + passwordDTO.getUserid());
        System.out.println("Password is : ----------------------------->" + passwordDTO.getNewPassword());

        if (results.hasErrors()) {
            System.out.println(results.getFieldErrors());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(results.getFieldErrors());
        }
        boolean b = this.itemService.changePassword(passwordDTO.getUserid(), passwordDTO.getNewPassword());
        if (b) {
            // password changed successfully
            return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
        } else {
            // password not changed successfully
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password not changed successfully");
        }
    }

    @GetMapping("/securePaymentPage")
    public String getPaymentPage() {
        return "paymentPage";
    }

    @GetMapping("/getPromoCodes")
    public ResponseEntity<List<PromoCode>> getAllPromoCodes() {
        try {
            List<PromoCode> promoCodes = this.itemService.getAllPromoCodes();
            promoCodes.forEach(code -> System.out.println(code));
            return ResponseEntity.status(HttpStatus.OK).body(promoCodes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/placeOrder")
    public String makeOrder(Model m, @RequestParam("customerid") String customerId,
            @RequestParam("shippingAddress") String shippingAddress, @RequestParam("totalAmount") String totalAmount,
            @RequestParam("paymentMethod") String paymentMethod, @RequestParam("tax") String tax,
            @RequestParam("offer") String offer) {

        Map<String, String> customerDetails = new HashMap<String, String>();
        customerDetails.put("customer_id", customerId);
        customerDetails.put("shipping_address", shippingAddress);
        customerDetails.put("total_amount", totalAmount);
        customerDetails.put("payment_method", paymentMethod);
        customerDetails.put("tax", tax);
        customerDetails.put("promo_code", offer);

        // now we have to make order object
        // then save the order in orde table
        // and we have to send message on customer email that your order is confirmed
        // and and placed

        // first get all cart items by using customer id
        List<ClothingItem> clothingItems = this.itemService.getAllClothingItemsFromCart(customerId);
        List<Object[]> cartItems = this.itemService.getItem_and_quan_From_Cart(customerId);
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        cartItems.forEach(item -> map.put((Integer) item[0], (Integer) item[1]));

        if (clothingItems != null) {
            // if clothing items are not null which is have to order by the customer
            // then create object of order class and save the object in database and send
            // the message to customer email
            // that your order is confirmed
            Boolean b = this.itemService.placeOrder(clothingItems, customerDetails);
            if (b) {
                // if order is confirmed and placed then send the message to customer
                this.emailService.sendEmail(customerId, "Order confirmed",
                        "Your order is confirmed and placed successfully!");
                // now your order is confirmed then we have to empty the cart
                this.itemService.emptyCart(customerId);
                List<Order> orders = this.itemService.getAllOrders(customerId);
                orders.forEach(order -> System.out.println(order.toString()));
                m.addAttribute("orders", orders);
                m.addAttribute("map", map);
                return "redirect:/myOrdersPage";
            } else {
                return "errorPage";
            }
        } else {
            return "errorPage";
        }

    }

    @PostMapping("/updateCartQuantity")
    public ResponseEntity<String> updateCartQuantity(@RequestBody List<CartUpdateDTO> cartItems,
            HttpServletRequest request) {
        // TODO: process POST request
        String customer_id = MyFunctions.get_User_Info_From_Cookies(request, "userid");
        System.out.println("Customer ID: " + customer_id);
        cartItems.forEach(item -> System.out.println(item.toString()));

        boolean b = this.itemService.updateCartItems(cartItems, customer_id);
        if (b) {
            return ResponseEntity.ok().body("Cart updated successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cart not updated successfully");

    }

    @GetMapping("/myOrdersPage")
    public String myOrderPage(HttpServletRequest request, Model m) {
        String customer_id = MyFunctions.get_User_Info_From_Cookies(request, "userid");
        if (customer_id == null) {
            System.out.println("Customer id is nullllllllllllllllllllllllll");
            m.addAttribute("login", "notlogin");
            return "orderSuccess";
        }
        m.addAttribute("username", MyFunctions.get_User_Info_From_Cookies(request, "username"));
        List<Order> orders = this.itemService.getAllOrders(customer_id);
        List<Object[]> cartItems = this.itemService.getItem_and_quan_From_Cart(customer_id);
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        cartItems.forEach(item -> map.put((Integer) item[0], (Integer) item[1]));
        m.addAttribute("orders", orders);
        m.addAttribute("map", map);
        return "orderSuccess";
    }

}
