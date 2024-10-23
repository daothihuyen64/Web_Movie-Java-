package com.webxemphim.demo.security;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webxemphim.demo.entity.User;
import com.webxemphim.demo.repository.LoginRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    LoginRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName((username));
        if(user == null) {
            throw new UsernameNotFoundException("User can't exist");
        }

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), new ArrayList<>());
    }

}
