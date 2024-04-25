package com.jpa.everythingshop.auth;

import com.jpa.everythingshop.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {

    User getUser();

    String getGradeCd();

    Integer getUserNum();

    Integer getHoldingPoint();
}
