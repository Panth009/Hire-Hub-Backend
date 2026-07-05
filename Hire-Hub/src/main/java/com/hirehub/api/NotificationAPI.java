package com.hirehub.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.dto.ResponseDTO;
import com.hirehub.entity.Notification;
import com.hirehub.exception.HireHubException;
import com.hirehub.service.NotificationService;

@Validated
@CrossOrigin
@RequestMapping("/notification")
@RestController
public class NotificationAPI 
{
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<List<Notification>> getnotifications(@PathVariable Long userId)
	{
		return new ResponseEntity<>(notificationService.unReadNotifications(userId), HttpStatus.OK);
	}

	@PutMapping("/read/{id}")
	public ResponseEntity<ResponseDTO> Readnotifications(@PathVariable Long id) throws HireHubException
	{
		notificationService.readNotification(id);
		return new ResponseEntity<>(new ResponseDTO("Success"), HttpStatus.OK);
	}
}
