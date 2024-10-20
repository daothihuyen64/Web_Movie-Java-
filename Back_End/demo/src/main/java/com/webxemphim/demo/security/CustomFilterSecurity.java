package com.webxemphim.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class CustomFilterSecurity {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    CustomerJwtFilter customerJwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {

        AuthenticationManagerBuilder  authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
                                    
        return authenticationManagerBuilder.build();
    } 

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        http.cors().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/login/**").permitAll() // Cho phép không cần xác thực cho endpoint đăng ký
            // .requestMatchers("/login/signin").permitAll() // Cần xác thực cho endpoint đăng nhập
            // .authenticated(); // phai chunng thuc
            .anyRequest() // nhung cai con lai khong dc quyen truy cap 
            .authenticated();
            // .permitAll();
            // .and()
            // .httpBasic();// dung jwt thi ko xai 2 dong nay nua

        http.addFilterBefore(customerJwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean 
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    }
}
