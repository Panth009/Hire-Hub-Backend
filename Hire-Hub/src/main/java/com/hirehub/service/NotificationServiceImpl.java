package com.hirehub.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirehub.dto.NotificationDTO;
import com.hirehub.dto.NotificationStatus;
import com.hirehub.entity.Notification;
import com.hirehub.exception.HireHubException;
import com.hirehub.repository.NotificationRepository;
import com.hirehub.utility.Utilities;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService
{
	@Autowired
	NotificationRepository notificationRepository;

	@Override
	public void sendNotification(NotificationDTO notificationDto) throws HireHubException
	{
		notificationDto.setId(Utilities.getSequence("notification"));
		notificationDto.setTimeStamp(LocalDateTime.now());
		notificationDto.setStatus(NotificationStatus.UNREAD);
		notificationRepository.save(notificationDto.toEntity());
	}

	@Override
	public List<Notification> unReadNotifications(Long userId)
	{
		return notificationRepository.findByUserIdAndStatus(userId, NotificationStatus.UNREAD);
	}

	@Override
	public void readNotification(Long id) throws HireHubException
	{
		Notification notification = notificationRepository.findById(id).orElseThrow(()->new HireHubException("Notification_Not_Exists"));
		notification.setStatus(NotificationStatus.READ);
		notificationRepository.save(notification);
	}
	
	
}
