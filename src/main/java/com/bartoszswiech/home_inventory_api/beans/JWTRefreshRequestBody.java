package com.bartoszswiech.home_inventory_api.beans;

public class JWTRefreshRequestBody {
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "JWTRefreshRequestBody{" +
                "refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
