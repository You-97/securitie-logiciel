package com.enumerations;


public enum AppUserAuthorities {

    USER_READ("user:read"),
    USER_WRITE("user:write");


    private final String authorities;

    AppUserAuthorities(String authorities) {
        this.authorities = authorities;
    }



}
