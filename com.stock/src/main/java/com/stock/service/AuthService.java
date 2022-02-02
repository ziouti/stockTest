package com.stock.service;

import com.stock.model.Personne;
import com.stock.repository.Auth;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service

public class AuthService implements AurthInterface, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final Auth dao;

    public AuthService(PasswordEncoder passwordEncoder, Auth dao) {
        this.passwordEncoder = passwordEncoder;
        this.dao = dao;
    }

/*    public void createPersonne(Personne personne) {
        try {
            personne.setPassword(passwordEncoder.encode(personne.getPassword()));
            dao.save(personne);
        } catch (Exception e) {

        }
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Personne user = dao.findByUsername(username);
        if (null != user) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public Personne findByUsername(String username) {
        return dao.findByUsername(username);

    }

    private Set<SimpleGrantedAuthority> getAuthority(Personne user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        SimpleGrantedAuthority simpleGrantedAuthority = null;
        if (user.getRole() == 1) {
            simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        }
        if (user.getRole() == 0) {
            simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        }
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }
}
