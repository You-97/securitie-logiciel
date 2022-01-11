package com.enumerations;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.enumerations.AppUserAuthorities.USER_READ;
import static com.enumerations.AppUserAuthorities.USER_WRITE;

public enum AppUserRole {

    ADMIN(Sets.newHashSet(USER_READ,USER_WRITE)),
    USER(Sets.newHashSet(USER_READ));


    private final Set<AppUserAuthorities> authorities;


    AppUserRole(Set<AppUserAuthorities> authorities) {
        this.authorities = authorities;
    }
}
