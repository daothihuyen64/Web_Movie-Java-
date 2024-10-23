package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInterface extends JpaRepository<User, Integer> {

    // Tìm người dùng dựa trên tên đăng nhập
    Optional<User> findByUserName(String username);
    
    // Tìm người dùng dựa trên email
    // Optional<User> findByEmail(String email);
}
