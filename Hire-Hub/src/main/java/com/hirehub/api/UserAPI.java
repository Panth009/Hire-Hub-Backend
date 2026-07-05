package com.hirehub.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.dto.LoginDTO;
import com.hirehub.dto.UserDTO;
import com.hirehub.dto.ResponseDTO;
import com.hirehub.exception.HireHubException;
import com.hirehub.service.UserService;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/users")
public class UserAPI 
{
	@Autowired
	public UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody @Validated UserDTO userDTO) throws HireHubException
	{
		userDTO = userService.registerUser(userDTO);
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<UserDTO> LoginUser(@RequestBody @Validated LoginDTO loginDTO) throws HireHubException
	{
		UserDTO userDTO = null;
		userDTO = userService.LoginUser(loginDTO);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	@PostMapping("/changePass")
	public ResponseEntity<ResponseDTO> changePassword(@RequestBody @Validated LoginDTO loginDTO) throws HireHubException
	{
		return new ResponseEntity<>(userService.changePassword(loginDTO), HttpStatus.OK);
	}
	
	@PostMapping("/sendOtp/{email}")
	public ResponseEntity<ResponseDTO> sendOtp(@PathVariable @Email(message = "{user.email.invalid}") String email) throws Exception
	{
		userService.sendOtp(email);
		return new ResponseEntity<>(new ResponseDTO("OTP sent succesfully"),HttpStatus.OK);
	} 
	
	@PostMapping("/verifyOtp/{email}/{otp}")
	public ResponseEntity<ResponseDTO> verifyOtp(@PathVariable @Email(message = "{user.email.invalid}") String email,@PathVariable @Pattern(regexp="^[0-9]{6}$", message="{otp.invalid}") String otp ) throws HireHubException
	{
		userService.verifyOtp(email, otp);
		return new ResponseEntity<>(new ResponseDTO("OTP verfied succesfully"),HttpStatus.ACCEPTED);
	} 
}
