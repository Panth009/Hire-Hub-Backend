package com.hirehub.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hirehub.dto.UserDTO;
import com.hirehub.exception.HireHubException;
import com.hirehub.service.UserService;

@Service
public class MyUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		try {
			UserDTO userDTO = userService.getUserByEmail(email);
			return new CustomUserDetails(userDTO.getId(), userDTO.getEmail(), userDTO.getName() ,userDTO.getPassword(), userDTO.getProfileId(), userDTO.getAccountType(), new ArrayList<>() );
		} catch (HireHubException e) {
						e.printStackTrace();
		}
		
		return null;
	}

}
