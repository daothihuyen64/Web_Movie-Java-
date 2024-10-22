package com.webxemphim.demo.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public boolean checkLogin(String username, String password) {

        User user = userRepository.findByUserName(username);

        return passwordEncoder.matches(password, user.getPassword());

    }

    @Override
    public ResponseData addUser(SignUpRequest signUpRequest) {
        ResponseData responseData = new ResponseData();

        if (userRepository.existsByNickName(signUpRequest.getNickName())) {
            responseData.setSuccess(false);
            responseData.setDesc("Nickname đã tồn tại.");
            return responseData;
        }

        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
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
        user.setUserName(signUpRequest.getUserName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); 
        user.setPhone(signUpRequest.getPhone());
        user.setNickName(signUpRequest.getNickName());
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

    // public boolean processForgotPassword(String userName) {
    //     User user = userRepository.findByUserName(userName);
    //     if (user != null) {
    //         // Tạo mật khẩu tạm thời
    //         String temporaryPassword = generateTemporaryPassword();

    //         // Mã hóa mật khẩu
    //         user.setPassword(passwordEncoder.encode(temporaryPassword));

    //         // Cập nhật mật khẩu vào cơ sở dữ liệu
    //         userRepository.save(user);

    //         // Gửi email
    //         String subject = "Mật khẩu mới của bạn";
    //         String body = "Mật khẩu tạm thời của bạn là: " + temporaryPassword + "\nHãy đổi mật khẩu sau khi đăng nhập.";
    //         sendSimpleEmail(user.getUserName(), subject, body);

    //         return true;
    //     }
    //     return false;
    // }

    // private String generateTemporaryPassword() {
    //     String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    //     StringBuilder password = new StringBuilder();
    //     Random random = new Random();
    //     for (int i = 0; i < 8; i++) {
    //         password.append(characters.charAt(random.nextInt(characters.length())));
    //     }
    //     return password.toString();
    // }

    // public void sendSimpleEmail(String toEmail, String subject, String body) {
    //     SimpleMailMessage message = new SimpleMailMessage();
    //     message.setTo(toEmail);
    //     message.setSubject(subject);
    //     message.setText(body);
    //     mailSender.send(message);
    // }

}
