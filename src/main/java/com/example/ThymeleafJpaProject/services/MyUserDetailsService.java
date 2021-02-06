package com.example.ThymeleafJpaProject.services;

import com.example.ThymeleafJpaProject.models.MyUserDetails;
import com.example.ThymeleafJpaProject.models.User;
import com.example.ThymeleafJpaProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Not found");
        }

        return new MyUserDetails(user);
    }
}
