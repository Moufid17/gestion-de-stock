package com.tmdigital.gestiondestock.services.auth;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.NotFoundEntityException;
import com.tmdigital.gestiondestock.model.User;
import com.tmdigital.gestiondestock.repository.UserRepository;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email).
                        orElseThrow(() -> new NotFoundEntityException("Not user with this email.", ErrorCodes.USER_NOT_FOUND));

        Collection<? extends GrantedAuthority> authorities = user.getRules().stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
            .collect(Collectors.toList());
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        // return new InMemoryUserDetailsManager(new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities));
    }

}
