package com.bridgelabz.fundo.user.service;

import org.springframework.stereotype.Service;
import com.bridgelabz.fundo.response.Response;
import com.bridgelabz.fundo.response.ResponseToken;
import com.bridgelabz.fundo.user.dto.LoginDto;
import com.bridgelabz.fundo.user.dto.UserDto;

@Service
public interface UserService 
{
     Response registerUser(UserDto userDto); 
     ResponseToken validateUser(LoginDto loginDto);  
     Response validateEmail(String token);
     Response forgotPassword( String emailId);
     Response resetPassword(String token,String newPassword);
     Response changePassword(String password);
   
}
