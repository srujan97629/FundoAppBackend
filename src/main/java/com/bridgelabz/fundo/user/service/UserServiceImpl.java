package com.bridgelabz.fundo.user.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.rabbitmq.MessageProducer;
import com.bridgelabz.fundo.response.Response;
import com.bridgelabz.fundo.response.ResponseToken;
import com.bridgelabz.fundo.user.dto.EmailIdDto;
import com.bridgelabz.fundo.user.dto.LoginDto;
import com.bridgelabz.fundo.user.dto.UserDto;
import com.bridgelabz.fundo.user.exceptions.UserException;
import com.bridgelabz.fundo.user.model.User;
import com.bridgelabz.fundo.user.repository.UserRepository;
import com.bridgelabz.fundo.utility.EmailService;
import com.bridgelabz.fundo.utility.ResponseStatus;
import com.bridgelabz.fundo.utility.TokenGenerator;

@PropertySource("classpath:message.properties")
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private TokenGenerator tokenGenerator;
	@Autowired
	private Environment environment;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	MessageProducer messageProduce;

	@Override
	public Response registerUser(UserDto userDto) {
		EmailIdDto emailIdDto = new EmailIdDto();
		Response response = null;
		Optional<User> userIsPresent = userRepository.findByEmailId(userDto.getEmailId());
		if (userIsPresent.isPresent()) {
			// throw new UserException(environment.getProperty("register.failure"), 101);
			response = ResponseStatus.sendResponse(environment.getProperty("register.failure"), 101);
		} else {
			User userProcess = modelMapper.map(userDto, User.class);
			String password = passwordEncoder.encode(userDto.getPassword());
			userProcess.setPassword(password);
			userProcess.setRegisteredDate(LocalDateTime.now());
			userProcess.setVerified(false);
			User status = userRepository.save(userProcess);
			// mail sending
			emailIdDto.setFrom(environment.getProperty("username"));
			emailIdDto.setTo(userDto.getEmailId());
			emailIdDto.setSubject("Verifying email");

			try {
				emailIdDto.setBody(emailService.getLink("http://localhost:8080/user/validateEmail", status.getId()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			messageProduce.sendMessage(emailIdDto);
			response = ResponseStatus.sendResponse(environment.getProperty("register.success"), 100);
		}

		return response;
	}

	@Override
	public ResponseToken validateUser(LoginDto loginDto) {
		ResponseToken response = null;
		User user = modelMapper.map(loginDto, User.class);
		System.out.println(user.getEmailId());
		Optional<User> userIsPresent = userRepository.findByEmailId(user.getEmailId());
//		System.out.println(userIsPresent);
//		System.out.println(loginDto.getEmailId());
		if (userIsPresent.isPresent()) {
			boolean status = passwordEncoder.matches(loginDto.getPassword(), userIsPresent.get().getPassword());
			if (status && userIsPresent.get().isVerified()) {
				try {

					String tokenGenerated = tokenGenerator.generateToken(userIsPresent.get().getId());
					System.out.println("login success");
					response = ResponseStatus.tokenStatus((environment.getProperty("login.success")), tokenGenerated,
							200);
				} catch (UnsupportedEncodingException e) {
					throw new UserException(environment.getProperty("login.wrong"), 202);
				}
			} else {
				response = ResponseStatus.tokenStatus((environment.getProperty("login.failure")), null, 201);
			}

		} else {
			System.out.println("ok");
			response = ResponseStatus.tokenStatus((environment.getProperty("login.failure.noUser")), null, 202);
			System.out.println(response);
		}
		System.out.println(response);

		return response;
	}

	@Override
	public Response validateEmail(String token) {
		Response response = null;
		long id = tokenGenerator.decodeToken(token);
		System.out.println(token);
		Optional<User> userIsPresent = userRepository.findByUserId(id).map(this::verify);
		if (userIsPresent.isPresent()) {
			System.out.println("verify");
			response = ResponseStatus.sendResponse(environment.getProperty("email.verify.success"), 5);
		} else {
			System.out.println("invalid");
			response = ResponseStatus.sendResponse(environment.getProperty("email.verify.err"), 6);
		}
		return response;
	}

	private User verify(User user) {
		user.setVerified(true);
		user.setModifiedDate(LocalDateTime.now());
		return userRepository.save(user);
	}

	@Override
	public Response forgotPassword(String emailId) {
		EmailIdDto emailidLink = new EmailIdDto();

		Response response = null;
		String email = emailId;
		Optional<User> userIsPresent = userRepository.findByEmailId(email);
		System.out.println(emailId);
		System.out.println(userIsPresent);

		if (userIsPresent.isPresent()) {
			emailidLink.setFrom(environment.getProperty("username"));
			emailidLink.setTo(emailId);
			emailidLink.setSubject("Forgot password");
			try {
				emailidLink.setBody(
						emailService.getLink("http://localhost:4200/user/resetpassword", userIsPresent.get().getId()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			emailService.sendMail(emailidLink);
			response = ResponseStatus.sendResponse(environment.getProperty("forgotpassword.success"), 250);
		} else {
			response = ResponseStatus.sendResponse(environment.getProperty("forgotpassword.failure"), 251);
		}

		return response;
	}

	@Override
	public Response resetPassword(String token, String newPassword) {
		Response response = null;
		long id = tokenGenerator.decodeToken(token);
		Optional<User> userIsPresent = userRepository.findById((long) id);
		if (userIsPresent.isPresent()) {
			String refPassword = passwordEncoder.encode(newPassword);
			userIsPresent.get().setPassword(refPassword);
			userIsPresent.get().setModifiedDate(LocalDateTime.now());
			userRepository.save(userIsPresent.get());
			response = ResponseStatus.sendResponse("resetPassword.success", 260);
		} else {
			response = ResponseStatus.sendResponse("resetPassword.failure", 261);
		}
		return response;
	}

	@Override
	public Response changePassword(String password) {

		return null;
	}

}
