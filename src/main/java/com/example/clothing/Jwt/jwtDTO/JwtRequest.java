package com.example.clothing.Jwt.jwtDTO;

public class JwtRequest {
    private String userid;
    private String password;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JwtRequest [userid=" + userid + ", password=" + password + "]";
    }

    public JwtRequest(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

}
