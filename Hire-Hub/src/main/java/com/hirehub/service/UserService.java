package com.hirehub.service;

import org.springframework.stereotype.Service;

import com.hirehub.dto.LoginDTO;
import com.hirehub.dto.ResponseDTO;
import com.hirehub.dto.UserDTO;
import com.hirehub.exception.HireHubException;

@Service
public interface UserService 
{
	public UserDTO registerUser(UserDTO userDTO) throws HireHubException;
	
	public UserDTO LoginUser(LoginDTO loginDTO) throws HireHubException;

	public Boolean sendOtp(String email) throws Exception;

	public Boolean verifyOtp(String email, String otp) throws HireHubException;

	public ResponseDTO changePassword(LoginDTO loginDTO) throws HireHubException;

	public UserDTO getUserByEmail(String email) throws HireHubException;
}
