package com.hirehub.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hirehub.dto.LoginDTO;
import com.hirehub.dto.NotificationDTO;
import com.hirehub.dto.ResponseDTO;
import com.hirehub.dto.UserDTO;
import com.hirehub.entity.OTP;
import com.hirehub.entity.User;
import com.hirehub.exception.HireHubException;
import com.hirehub.repository.OTPRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.utility.Data;
import com.hirehub.utility.Utilities;

import jakarta.mail.internet.MimeMessage;

@Service(value="userService")
public class UserServiceImpl implements UserService
{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private OTPRepository otpRepository;
	
	@Autowired
	ProfileService profileService;
	
	@Autowired
	NotificationService notificationService;

	@Override
	public UserDTO registerUser(UserDTO userDTO) throws HireHubException 
	{
		Optional<User> optional = userRepository.findByEmail(userDTO.getEmail());
		
		if(optional.isEmpty()) 
		{
			userDTO.setId(Utilities.getSequence("users"));
			userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			userDTO.setProfileId(profileService.createProfile(userDTO.getEmail()));
			User user = userDTO.toEntity();
			user = userRepository.save(user);
			return user.toDTO();
		}
		else 
		{
			throw new HireHubException("USER_FOUND");
		}
	}

	@Override
	public UserDTO LoginUser(LoginDTO loginDTO) throws HireHubException {
		User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new HireHubException("USER_NOT_FOUND"));
		if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) 
		{
			throw new HireHubException("INVALID_CREDENTIALS");
		}
		return user.toDTO();
	}

	@Override
	public Boolean sendOtp(String email) throws Exception
	{
		User user = userRepository.findByEmail(email).orElseThrow(()-> new HireHubException("User_Not_Found"));
		
		MimeMessage mm = mailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mm, true);
		
		message.setTo(email);
		message.setSubject("Your OTP Code");
		
		String genOTP = Utilities.generateOTP();
		
		OTP otp = new OTP(email, genOTP, LocalDateTime.now());
		
		otpRepository.save(otp);
		
		String messageBody = Data.getMessageBody(genOTP,user.getName());
		
		message.setText(messageBody, true);
		
		mailSender.send(mm);
		
		return true;
	}

	@Override
	public Boolean verifyOtp(String email, String otp) throws HireHubException 
	{
		OTP otpObj = otpRepository.findById(email).orElseThrow(()-> new HireHubException("OTP_NOT_FOUND"));
		
		if(!otpObj.getOtp().equals(otp)) 
		{
			throw new HireHubException("OTP_NOT_MATCHED"); 
		}
		
		return true;
	}

	@Override
	public ResponseDTO changePassword(LoginDTO loginDTO) throws HireHubException {

		User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new HireHubException("USER_NOT_FOUND"));
		user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
		userRepository.save(user);
		
		NotificationDTO notificationDTO = new NotificationDTO();
		notificationDTO.setUserId(user.getId());
		notificationDTO.setAction("Password Reset");
		notificationDTO.setMessage("Password Changed Successfully...!");
		notificationService.sendNotification(notificationDTO);
		
		return new ResponseDTO("Password Changed Succsefully.");
	}
	
	@Scheduled(fixedRate = 60000)
	public void removeExpiredOTP() 
	{
		
		LocalDateTime expiry = LocalDateTime.now().minusMinutes(5);
		
		List<OTP> expireOTPs = otpRepository.findByCreationTimeBefore(expiry);
		
		if(!expireOTPs.isEmpty()) 
		{
			otpRepository.deleteAll(expireOTPs);
			System.out.println("Removed "+expireOTPs.size() + " Expired OTP's.");
		}
		
	}

	@Override
	public UserDTO getUserByEmail(String email) throws HireHubException {
		
		return userRepository.findByEmail(email).orElseThrow(()->new HireHubException("USER_NOT_FOUND")).toDTO();
	}
	
}
