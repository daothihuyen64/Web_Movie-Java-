package com.webxemphim.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webxemphim.demo.entity.User;

@Repository
public interface LoginRepository extends JpaRepository<User, Integer>{

    List<User> findByUserNameAndPassword(String userName, String password);
    User findByUserName(String userName);
    User findByNickName(String nickName);
    boolean existsByUserName(String userName);
    boolean existsByNickName(String nickName);
    boolean existsByPhone(String phone);
}
