package com.webxemphim.demo.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.webxemphim.demo.entity.Role;
import com.webxemphim.demo.entity.User;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.payload.request.SignUpRequest;
import com.webxemphim.demo.repository.LoginRepository;
import com.webxemphim.demo.service.imp.LoginServiceImp;


@Service
public class LoginService implements LoginServiceImp{

    @Autowired
    // @Qualifier("ten Bean")
    LoginRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public boolean checkLogin(String username, String password) {

        User user = userRepository.findByUserName(username);

        return passwordEncoder.matches(password, user.getPassword());

    }

    @Override
    public ResponseData addUser(SignUpRequest signUpRequest) {
        ResponseData responseData = new ResponseData();

        if (!isValidEmail(signUpRequest.getUsername())) { 
            responseData.setSuccess(false);
            responseData.setDesc("Email không hợp lệ.");
            return responseData;
        }

        if (userRepository.existsByNickName(signUpRequest.getNickname())) {
            responseData.setSuccess(false);
            responseData.setDesc("Nickname đã tồn tại.");
            return responseData;
        }

        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            responseData.setSuccess(false);
            responseData.setDesc("Username đã tồn tại.");
            return responseData;
        }
    
        if (!isValidPassword(signUpRequest.getPassword())) {
            responseData.setSuccess(false);
            responseData.setDesc("Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất một chữ cái in hoa, một chữ số và một ký tự đặc biệt.");
            return responseData;
        }

        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            responseData.setSuccess(false);
            responseData.setDesc("Mật khẩu không khớp.");
            return responseData;
        }

        if (userRepository.existsByPhone(signUpRequest.getPhone())) {
            responseData.setSuccess(false);
            responseData.setDesc("Số điện thoại đã tồn tại.");
            return responseData;
        }
    
        Role role = new Role();
        role.setId(1);

        User user = new User();
        user.setUserName(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); 
        user.setPhone(signUpRequest.getPhone());
        user.setNickName(signUpRequest.getNickname());
        user.setRole(role);
        
        try {
            userRepository.save(user);
            responseData.setSuccess(true);
            responseData.setDesc("Đăng ký thành công.");
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDesc("Đã xảy ra lỗi khi lưu người dùng.");
        }
        return responseData;
    }

    public boolean isValidPassword(String password) {
        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*") || !password.matches(".*[^a-zA-Z0-9].*")) {
            return false; 
        }
        return true;
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }  

}
