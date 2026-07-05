package com.hirehub.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hirehub.dto.NotificationStatus;
import com.hirehub.entity.Notification;

public interface NotificationRepository extends MongoRepository<Notification, Long>
{
	public List<Notification> findByUserIdAndStatus(Long userId, NotificationStatus status);
}
