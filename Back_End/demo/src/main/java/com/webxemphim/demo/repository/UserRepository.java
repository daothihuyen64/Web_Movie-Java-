package com.webxemphim.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.webxemphim.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUserName(String username);
    boolean existsByNickName(String nickname);
}
