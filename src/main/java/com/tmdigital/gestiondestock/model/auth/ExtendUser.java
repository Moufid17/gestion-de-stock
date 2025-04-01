package com.tmdigital.gestiondestock.model.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

public class ExtendUser extends User {

    @Setter
    @Getter
    private String companyId;

    public ExtendUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    
    public ExtendUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String companyId) {
        super(username, password, authorities);
        this.companyId = companyId;
    }

}
