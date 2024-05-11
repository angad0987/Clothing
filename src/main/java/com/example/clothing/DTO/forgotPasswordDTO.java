package com.example.clothing.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class forgotPasswordDTO {
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must: Be at least 8 characters long, Contain at least one digit (0-9), Contain at least one lowercase letter (a-z), Contain at least one uppercase letter (A-Z), Contain at least one special character from @#$%^&+=, Not contain any whitespace characters")
    public String newpassword;
    @NotBlank(message = "Email cannot be  empty")
    public String userid;

    public String getNewPassword() {
        return newpassword;
    }

    public void setNewPassword(String newPassword) {
        this.newpassword = newPassword;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
