package com.example.clothing.Jwt.jwtDTO;

public class JwtResponse {

    private String jwtToken;
    private String username;

    public String getJwtToken() {
        return jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String jwtToken;
        private String username;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
            return this;
        }

        public JwtResponse build() {
            return new JwtResponse(this);
        }

    }

    // private constructor for creating object of JwtResponse class
    private JwtResponse(Builder builder) {
        this.jwtToken = builder.jwtToken;
        this.username = builder.username;
    }

}
