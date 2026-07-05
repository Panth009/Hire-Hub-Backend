package com.hirehub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirehub.dto.NotificationDTO;
import com.hirehub.entity.Notification;
import com.hirehub.exception.HireHubException;

@Service
public interface NotificationService 
{	
	 public void sendNotification(NotificationDTO notificationDto) throws HireHubException;
	 
	 public List<Notification> unReadNotifications(Long userId);

	 public void readNotification(Long id) throws HireHubException;
}
