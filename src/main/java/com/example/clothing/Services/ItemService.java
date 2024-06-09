package com.example.clothing.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.clothing.DAO.CartRepository;
import com.example.clothing.DAO.ClothingItemRepository;
import com.example.clothing.DAO.OrderRepository;
import com.example.clothing.DAO.OtpRepository;
import com.example.clothing.DAO.PromoCodeRepository;
import com.example.clothing.DAO.TokenRepository;
import com.example.clothing.DAO.UserRepository;
import com.example.clothing.DAO.WishlistRepository;
import com.example.clothing.DTO.CartUpdateDTO;
import com.example.clothing.DTO.SignUpDTO;
import com.example.clothing.Entities.Cart;
import com.example.clothing.Entities.ClothingItem;
import com.example.clothing.Entities.OTP;
import com.example.clothing.Entities.Order;
import com.example.clothing.Entities.PromoCode;
import com.example.clothing.Entities.Token;
import com.example.clothing.Entities.User;
import com.example.clothing.Entities.Wishlist;
import com.example.clothing.oAuthConfig.Provider;

@Service
@Component
public class ItemService {
    @Autowired
    private OrderRepository order_Repository;

    @Autowired
    private PromoCodeRepository promoCodeRepository;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ClothingItemRepository clothingItemRepository;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<String> getItemTypes(String condition, String gender) {
        return clothingItemRepository.getTypes(condition, gender);
    }

    public List<ClothingItem> getItems(String type, String gender) {
        return clothingItemRepository.findByTypeAndGender(type, gender);
    }

    public List<ClothingItem> getItemsByType(String type) {
        return clothingItemRepository.findByType(type);
    }

    public String getUsername(String userid, String password) {
        return userRepository.checkLogin(userid, password);
    }

    public void CreateNewUser(User user) {
        System.out.println("Provider is ----------------------------->" + user.getProvider());
        userRepository.save(user);
    }

    public List<String> getCollectionTypes(String weather) {
        System.out.println("I am in user service method ");
        List<String> list = clothingItemRepository.getCollectionTypes(weather);
        System.out.println("Size of list is : " + list.size());

        return list;
    }

    public void addToCart(String item_id, String customer_id, int quan) {

        // first we will check is there any item with item_id of customer_id exists in
        // database then not create new object
        // only update the quantity
        Cart cartItem = cartRepository.getByCustomerIdAndItemid(customer_id, Integer.parseInt(item_id));
        if (cartItem != null) {
            System.out.println("----------------------------------------------->Item is already exists");
            cartItem.setQuantity(cartItem.getQuantity() + quan);
            cartRepository.save(cartItem);
            return;
        }
        // if not then create new object and save it in database
        Cart cart = new Cart();
        cart.setItem_id(Integer.parseInt(item_id));
        cart.setCustomer_id(customer_id);
        cart.setQuantity(quan);
        cartRepository.save(cart);

    }

    public List<Object[]> getCartItems(String customer_id) {
        return cartRepository.getCartItems(customer_id);
    }

    public void RemoveFromCart(int item_id, String customer_id) {
        cartRepository.deleteByCustomeridAndItemid(customer_id, item_id);
    }

    public void AddtoWishlist(Wishlist wishlistItem) {
        wishlistRepository.save(wishlistItem);
    }

    public boolean isInWishlist(String item_id, String user_id) {
        return wishlistRepository.existsByItemidAndUserid(Integer.parseInt(item_id), user_id);
    }

    public List<Integer> getWishlistItemsIds(String userId) {
        return wishlistRepository.getWishlistItemIds(userId);
    }

    public List<Wishlist> getWishlistItems(String user_id) {
        List<Object[]> wishlistArray = wishlistRepository.getWishlistItems(user_id);
        System.out.println("---------------------------------------------->" + wishlistArray.size());
        List<Wishlist> anslist = new ArrayList<>();
        for (Object[] item : wishlistArray) {
            Wishlist wishlistItem = new Wishlist();
            BigDecimal quantityBigDecimal = (BigDecimal) item[3];
            wishlistItem.setId((Integer) item[0]);
            wishlistItem.setItemname((String) item[1]);
            wishlistItem.setItemprice((Float) item[2]);
            wishlistItem.setQuantity(quantityBigDecimal.longValue());
            wishlistItem.setCategory((String) item[4]);
            wishlistItem.setDescription((String) item[5]);
            wishlistItem.setItem_id((Integer) item[6]);
            wishlistItem.setUser_id((String) item[7]);
            anslist.add(wishlistItem);
        }
        return anslist;
    }

    public List<Wishlist> getWishlistItemsTypes(String user_id) {
        // we have to get related items also on the basis of user gender
        User user = userRepository.findByUserId(user_id);
        List<Object[]> list = wishlistRepository.getWishlistItemTypes(user_id, user.getGender());
        List<Wishlist> anslist = new ArrayList<>();
        for (Object[] item : list) {
            Wishlist wishlistItem = new Wishlist();
            wishlistItem.setItemname((String) item[4]);
            wishlistItem.setItemprice((Float) item[7]);
            wishlistItem.setItem_id((Integer) item[0]);
            wishlistItem.setQuantity(1);
            wishlistItem.setCategory((String) item[5]);
            wishlistItem.setDescription((String) item[6]);
            anslist.add(wishlistItem);
        }
        return anslist;
    }

    public String RemoveFromWishlist(int item_id, String custoemr_id) {
        try {
            wishlistRepository.deleteFromWishlist(custoemr_id, item_id);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }

    public ClothingItem getSingleItem(int itemid) {
        return clothingItemRepository.findById(itemid);
    }

    public User getUser(String customer_id) {
        return userRepository.findById(customer_id).get();
    }

    public void processOAuthPostLogin(String username) {
        User existUser = userRepository.getUsername(username);

        if (existUser == null) {
            User user = new User();
            user.setUserId(username);
            user.setProvider(Provider.GOOGLE);

            userRepository.save(user);

        } else {
            existUser.setProvider(Provider.GOOGLE);
            userRepository.save(existUser);
        }

    }

    public User getUserFromSignUpDTO(SignUpDTO signUpDTO) {
        User user = new User();
        user.setUserId(signUpDTO.getUserId());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setUsername(signUpDTO.getUsername());
        user.setCity(signUpDTO.getCity());
        user.setCountry(signUpDTO.getCountry());
        user.setHouse_no(signUpDTO.getHouse_no());
        user.setState(signUpDTO.getState());
        user.setPincode(Integer.parseInt(signUpDTO.getPincode()));
        user.setGender(signUpDTO.getGender());
        user.setStreet_name(signUpDTO.getStreet_name());
        user.setProvider(Provider.LOCAL);
        return user;

    }

    public List<ClothingItem> getItemsByTypeAndGender(String type, String gender) {
        List<ClothingItem> list = clothingItemRepository.findByTypeAndGender(type, gender);
        System.out.println(list);
        if (list != null)
            return list;
        else
            return Collections.emptyList();
    }

    public List<ClothingItem> getItemsByTypeAndGenderAndPriceRange(String type, String gender, Integer min,
            Integer max) {
        List<ClothingItem> list = clothingItemRepository.findByTypeAndGenderAndPriceBetween(type, gender, min, max);

        System.out.println("CLOTHING ITEMS ARE ---------------------------------------------");
        list.forEach(clothingItem -> System.out.println(clothingItem.toString()));
        if (list != null)
            return list;
        else
            return Collections.emptyList();
    }

    public List<ClothingItem> getItemsByTypeAndGenderAndDiscount(String type, String gender, Float discount) {
        List<ClothingItem> list = clothingItemRepository.getByTypeAndGenderAndDiscount(type, gender, discount);
        System.out.println(list);
        if (list != null) {
            return list;
        } else
            return Collections.emptyList();
    }

    public List<ClothingItem> getItemsByTypeAndGenderAndColor(String type, String gender, String color) {
        List<ClothingItem> list = clothingItemRepository.findByTypeAndGenderAndColor(type, gender, color);
        System.out.println(list);
        if (list != null) {
            return list;
        } else
            return Collections.emptyList();
    }

    public List<ClothingItem> getItemsByTypeAndGenderAndDiscountAndColor(String typeString, String gender,
            Float discount, String color) {
        List<ClothingItem> list = clothingItemRepository.getByTypeAndGenderAndDiscountAndColor(typeString, gender,
                discount, color);
        System.out.println(list);
        if (list != null) {
            return list;
        } else
            return Collections.emptyList();
    }

    public List<ClothingItem> getItemsByTypeAndGenderAndPriceRangeAndColor(String type, String gender, Integer min,
            Integer max, String color) {
        List<ClothingItem> list = clothingItemRepository.findByTypeAndGenderAndColorAndPriceBetween(type, gender, color,
                min, max);
        System.out.println(list);
        if (list != null) {
            return list;
        } else
            return Collections.emptyList();
    }

    public List<ClothingItem> getItemsByTypeAndGenderAndPriceRangeAndDiscount(String type, String gender, Integer min,
            Integer max, Float discount) {
        List<ClothingItem> list = clothingItemRepository.getByTypeAndGenderAndDiscountAndPriceRange(type, gender,
                discount, min, max);
        System.out.println(list);
        if (list != null) {
            return list;
        } else
            return Collections.emptyList();
    }

    public List<ClothingItem> getItemsByTypeAndGenderAndPriceRangeAndDiscountAndColor(String type, String gender,
            Integer min, Integer max, Float discount, String color) {
        List<ClothingItem> list = clothingItemRepository.getByTypeAndGenderAndDiscountAndPriceRangeAndColor(type,
                gender, discount, min, max, color);
        System.out.println(list);
        if (list != null) {
            return list;
        } else
            return Collections.emptyList();
    }

    public void emptyCart(String customer_id) {
        try {
            cartRepository.deleteByCustomerid(customer_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveToken(Token token) {
        tokenRepository.save(token);
    }

    public List<Token> getAllTokens(String customer_id) {
        return this.tokenRepository.findBy_userid(customer_id);
    }

    public void saveAllTokens(List<Token> tokens) {
        this.tokenRepository.saveAll(tokens);
    }

    public Optional<Token> findByToken(String token) {
        return this.tokenRepository.findByToken(token);
    }

    public void saveOtpInDatabase(String email, int otp) {
        // WE HAVE TO SAVE THE OTP SECURELY IN DATABASE USING BCRYPT PASSWORD ENCRYPTER
        System.out.println(email);
        OTP otp1 = new OTP();
        otp1.setEmail(email);
        otp1.setOtpCode(passwordEncoder.encode(String.valueOf(otp)));
        otp1.setCreatedAT(LocalDateTime.now());
        otp1.setExpiresAT(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otp1);

        System.out.println("OTP IS SAVED SUCCESSFULLY");
    }

    @Scheduled(fixedRate = 300000)
    public void deleteExpiredOtp() {
        System.out.println("i am in deleteExpiredOtp method");
        otpRepository.deleteByExpiresATLessThan(LocalDateTime.now());
        System.out.println("Expired OTPs are deleted");
    }

    public boolean validateOtp(String userId, int otp) {
        String userOtp = String.valueOf(otp);
        OTP otp2 = otpRepository.getlatestOTPByEmail(userId);
        if (otp2 != null) {
            String otpFromDatabase = otp2.getOtpCode();
            System.out.println("OTP FROM DATABASE IS :" + otp2.getOtpCode());
            System.out.println("OTP USER ENTERED : " + passwordEncoder.encode(userOtp));
            if (otp2.getExpiresAT().isBefore(LocalDateTime.now())) {
                // means otp is expired
                return false;
            }
            if (passwordEncoder.matches(userOtp, otpFromDatabase)) {
                System.out.println("Password matches----------------->");
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean userAlreadyExist(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            System.out.println("User already exists");
            System.out.println(userOptional.get().toString());
            return true;
        }
        return false;
    }

    public boolean changePassword(String userid, String newPassword) {
        try {
            User user = userRepository.findById(userid).get();
            user.setPassword(passwordEncoder.encode(newPassword));
            this.userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<PromoCode> getAllPromoCodes() {
        return (List<PromoCode>) this.promoCodeRepository.findAll();
    }

    public boolean updateCartItems(List<CartUpdateDTO> cartItems, String customerid) {
        System.out.println(customerid);
        try {
            for (CartUpdateDTO cartItems2 : cartItems) {
                System.out.println(cartItems2.getItemid());
                Cart cartitem = cartRepository.getByCustomerIdAndItemid(customerid,
                        Integer.parseInt(cartItems2.getItemid()));
                cartitem.setQuantity(cartItems2.getQuantity());
                cartRepository.save(cartitem);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public List<ClothingItem> getAllClothingItemsFromCart(String customerId) {
        // getting all items id's from cart respective to the customer id
        List<Integer> list = cartRepository.getAllClothingItemsIdsFromCart(customerId);
        System.out.println("Items id's are ");
        list.forEach(item -> System.out.println(item));
        // getting all clothing items from database
        List<ClothingItem> list1 = (List<ClothingItem>) clothingItemRepository.findAllById(list);
        System.out.println("Items are ");
        list1.forEach(item -> System.out.println(item.toString()));
        return list1;
    }

    public Boolean placeOrder(List<ClothingItem> clothingItems, Map<String, String> customerDetails) {
        // now create object of Order class
        try {
            Order order = new Order(clothingItems, customerDetails);
            System.out.println(order.toString());
            order_Repository.save(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Order> getAllOrders(String customer_id) {
        return order_Repository.findByCustomerid(customer_id);
    }

    public List<Object[]> getItem_and_quan_From_Cart(String customerId) {
        try {
            List<Object[]> cartItems = this.cartRepository.getItemIdAndQuantity(customerId);
            if (cartItems != null) {
                return cartItems;
            } else
                return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}