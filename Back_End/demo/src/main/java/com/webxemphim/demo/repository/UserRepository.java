package com.webxemphim.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.webxemphim.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUserName(String username);
}
