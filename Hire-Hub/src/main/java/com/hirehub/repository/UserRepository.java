package com.hirehub.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hirehub.entity.User;

public interface UserRepository extends MongoRepository<User, Long>
{
	public Optional<User> findByEmail(String email);
}
