package com.webxemphim.demo.service.imp;

import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.payload.request.SignUpRequest;

public interface LoginServiceImp {
    // List<UserDTO> getAllUser();
    boolean checkLogin(String username, String password);
    ResponseData addUser(SignUpRequest signUpRequest);
}
