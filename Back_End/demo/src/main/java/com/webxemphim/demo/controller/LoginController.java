package com.webxemphim.demo.controller;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.webxemphim.demo.payload.ResponseData;
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
    private AuthenticationManager authenticationManager;


    @Autowired
    JwtUtilsHelper jwtUtilsHelper;

    //Đăng nhập
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password) {
       ResponseData responseData = new ResponseData();

        // SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // String encrypted = Encoders.BASE64.encode(secretKey.getEncoded());
        // System.out.println(encrypted);

       if(loginServiceImp.checkLogin(username, password)) {
            String token = jwtUtilsHelper.generateToken(username);
            responseData.setData(token);
        
       }
       else {
            responseData.setData("");
            responseData.setSuccess(false);
       }
        
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    //Đăng kí
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        ResponseData responseData = new ResponseData();

        responseData.setData(loginServiceImp.addUser(signUpRequest));

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    //Đăng xuất 
    @PostMapping("/logout")
    public ResponseEntity<ResponseData> logout(@RequestParam String token) {
        // Thêm token vào blacklist
        jwtUtilsHelper.addTokenToBlacklist(token);

        // Tạo đối tượng ResponseData để trả về
        ResponseData responseData = new ResponseData();
        responseData.setStatus(HttpStatus.OK.value()); // 200 OK
        responseData.setDesc("Logout successful");
        responseData.setData(null); // Không có dữ liệu thêm

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // @PostMapping("/forgotpassword")
    // public ResponseEntity<String> forgotPassword(@RequestParam String username) {
    //     ResponseData responseData = new ResponseData();
    //     boolean isProcessed = loginService.processForgotPassword(username);
    //     if (isProcessed) {
    //         return ResponseEntity.ok("Mật khẩu mới đã được gửi tới email của bạn.");
    //     } else {
    //         return ResponseEntity.badRequest().body("Không tìm thấy người dùng với email này.");
    //     }
    // }

}
