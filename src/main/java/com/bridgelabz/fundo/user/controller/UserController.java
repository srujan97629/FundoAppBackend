package com.bridgelabz.fundo.user.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.response.Response;
import com.bridgelabz.fundo.response.ResponseToken;
import com.bridgelabz.fundo.user.dto.LoginDto;
import com.bridgelabz.fundo.user.dto.UserDto;
import com.bridgelabz.fundo.user.service.UserService;

@RestController
@CrossOrigin(allowedHeaders = "*",origins = "*")
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	UserService userService;
	
	@PostMapping("/register")	
	public ResponseEntity<Response> register(@RequestBody UserDto userDto) throws UnsupportedEncodingException
	{
		System.out.println(userDto);
		Response response=userService.registerUser(userDto );
		System.out.println(response);		
		return  new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseToken> login(@RequestBody LoginDto loginDto) 
	{
		
		ResponseToken responseToken=userService.validateUser(loginDto);
		return new ResponseEntity<>(responseToken,HttpStatus.OK);
	}
	
	@GetMapping("/validateEmail/{token}")
	public ResponseEntity<Response> validateEmail(@PathVariable String token)
	{
		Response response=userService.validateEmail(token);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/forgotPassword")
	public ResponseEntity<Response> forgot(@RequestParam(name="emailId") String emailId)
	{
		System.out.println("ok");
		Response response=userService.forgotPassword(emailId);
		return new ResponseEntity<>(response,HttpStatus.OK); 
	}
	@PutMapping("/resetpassword/{token}")
	public ResponseEntity<Response> resetPassword(@RequestHeader(name = "token") String token,@RequestParam(name="newPassword") String newPassword)       
	{
		
		Response response=userService.resetPassword( token,newPassword);
		
		return new ResponseEntity<>(response,HttpStatus.OK); 
	}
	
   
}





