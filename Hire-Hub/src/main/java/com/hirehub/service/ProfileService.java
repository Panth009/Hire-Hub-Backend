package com.hirehub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirehub.dto.ProfileDTO;
import com.hirehub.exception.HireHubException;

@Service
public interface ProfileService 
{
	public Long createProfile(String email) throws HireHubException;
	public ProfileDTO getProfile(Long profileId) throws HireHubException;
	public ProfileDTO updateProfile(ProfileDTO profileDTO) throws HireHubException;
	public List<ProfileDTO> getAllProfiles() throws HireHubException;
}
