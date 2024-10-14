/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webmovie.yeuphim.controller;

import com.webmovie.yeuphim.entity.User;
import com.webmovie.yeuphim.repository.UserInterface;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserInterface userInterface;
    
    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestParam String userName, @RequestParam String password) {
        List<User> listUser = userInterface.findAll();
        for(User user: listUser) {
            System.out.println(user.getUserName());
        }
        
        return new ResponseEntity<>("", HttpStatus.OK);
        
    }
}