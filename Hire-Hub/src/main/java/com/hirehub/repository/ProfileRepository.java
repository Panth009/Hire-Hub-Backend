package com.hirehub.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hirehub.entity.Profile;

public interface ProfileRepository extends MongoRepository<Profile, Long>
{

}
