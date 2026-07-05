package com.hirehub.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirehub.dto.ProfileDTO;
import com.hirehub.entity.Profile;
import com.hirehub.exception.HireHubException;
import com.hirehub.repository.ProfileRepository;
import com.hirehub.utility.Utilities;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService
{

	@Autowired
	ProfileRepository profileRepository;
	
	@Override
	public Long createProfile(String email) throws HireHubException
	{
		Profile profile = new Profile();
		
		profile.setId(Utilities.getSequence("profiles"));
		profile.setEmail(email);
		profile.setSkills(new ArrayList<>());
		profile.setExperiences(new ArrayList<>());
		profile.setCertifications(new ArrayList<>());
		
		profileRepository.save(profile);
		
		return profile.getId();
	}

	@Override
	public ProfileDTO getProfile(Long profileId) throws HireHubException 
	{
		return profileRepository.findById(profileId).orElseThrow(()->new HireHubException("PROFILE_NOT_FOUND")).toDTO();
	}

	@Override
	public ProfileDTO updateProfile(ProfileDTO profileDTO) throws HireHubException 
	{
		profileRepository.findById(profileDTO.getId()).orElseThrow(()->new HireHubException("PROFILE_NOT_FOUND"));
		profileRepository.save(profileDTO.toEntity());
		return profileDTO;
	}

	@Override
	public List<ProfileDTO> getAllProfiles() throws HireHubException 
	{
		return profileRepository.findAll().stream().map((x)->x.toDTO()).toList();
	}
	
}
