package com.webxemphim.demo.controller;

import java.util.Base64;

import javax.crypto.SecretKey;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.webxemphim.demo.repository.LoginRepository;

import com.webxemphim.demo.entity.User;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.payload.request.LoginRequest;
import com.webxemphim.demo.payload.request.SignUpRequest;
import com.webxemphim.demo.service.LoginService;
import com.webxemphim.demo.service.imp.LoginServiceImp;
import com.webxemphim.demo.utils.JwtUtilsHelper;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    LoginServiceImp loginServiceImp;

    @Autowired
    LoginService loginService;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    JwtUtilsHelper jwtUtilsHelper;

    //Đăng nhập
    @PostMapping("/signin")
    public ResponseEntity<ResponseData> signin(@RequestBody LoginRequest loginRequest) {
       ResponseData responseData = new ResponseData();

        // SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // String encrypted = Encoders.BASE64.encode(secretKey.getEncoded());
        // System.out.println(encrypted);

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

       if(loginServiceImp.checkLogin(username, password)) {
            String token = jwtUtilsHelper.generateToken(username);

            User user = loginRepository.findByUserName(username);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", user.getId());
            response.put("nickname", user.getNickName());

            responseData.setData(response);
            responseData.setDesc("Đăng nhập thành công.");
       }
       else {
            responseData.setData("");
            responseData.setSuccess(false);
            responseData.setDesc("Đăng nhập thất bại.");
       }
        
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    //Đăng kí
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {

        ResponseData responseData = loginServiceImp.addUser(signUpRequest);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    //Đăng xuất 
    @PostMapping("/logout")
    public ResponseEntity<ResponseData> logout(@RequestParam String token) {
        // Thêm token vào blacklist
        jwtUtilsHelper.addTokenToBlacklist(token);

        ResponseData responseData = new ResponseData();
        responseData.setDesc("Đăng xuất thành công.");
        responseData.setData(null); 

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
