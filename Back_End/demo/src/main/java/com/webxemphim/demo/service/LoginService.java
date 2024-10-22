package com.webxemphim.demo.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.webxemphim.demo.entity.Role;
import com.webxemphim.demo.entity.User;
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

    // @Autowired
    // private JavaMailSender mailSender;


    @Override
    public boolean checkLogin(String username, String password) {

        User user = userRepository.findByUserName(username);

        return passwordEncoder.matches(password, user.getPassword());

    }

    @Override
    public boolean addUser(SignUpRequest signUpRequest) {

        if (userRepository.existsByNickName(signUpRequest.getNickName())) {
            throw new IllegalArgumentException("Nickname already exists");
        }

        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            throw new IllegalArgumentException("Username already exists");
        }
    
        if (!isValidPassword(signUpRequest.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, contain at least one uppercase letter, one number, and one special character.");
        }

        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (userRepository.existsByPhone(signUpRequest.getPhone())) {
            throw new RuntimeException("Phone number already exists.");
        }
    
        Role role = new Role();
        role.setId(signUpRequest.getRoleId());

        User user = new User();
        user.setUserName(signUpRequest.getUserName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); // Mã hóa mật khẩu
        user.setPhone(signUpRequest.getPhone());
        user.setNickName(signUpRequest.getNickName());
        user.setRole(role);
        
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidPassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("Password must contain at least one number.");
        }
        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character.");
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
