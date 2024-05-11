package com.example.clothing.DTO;

import com.example.clothing.oAuthConfig.Provider;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class SignUpDTO {

    // Be at least 8 characters long
    // Contain at least one digit ([0-9])
    // Contain at least one lowercase letter ([a-z])
    // Contain at least one uppercase letter ([A-Z])
    // Contain at least one special character from the set @#$%^&+=
    // Not contain any whitespace characters (\S+)
    @NotBlank(message = "Email address cant't be empty")
    public String userId;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must: Be at least 8 characters long, Contain at least one digit (0-9), Contain at least one lowercase letter (a-z), Contain at least one uppercase letter (A-Z), Contain at least one special character from @#$%^&+=, Not contain any whitespace characters")
    public String password;

    @NotBlank(message = "Username must not be empty")
    public String username;

    @NotBlank(message = "Gender must not be empty")
    public String gender;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits long")
    public String mobileno;

    @NotBlank(message = "Pincode must not be empty")
    public String pincode;

    @NotBlank(message = "House number must not be empty")
    public String house_no;

    @NotBlank(message = "Street name must not be empty")
    public String street_name;

    @NotBlank(message = "City must not be empty")
    public String city;

    @NotBlank(message = "State must not be empty")
    public String state;

    @NotBlank(message = "Country must not be empty")
    public String country;

    public int otp;

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

}
