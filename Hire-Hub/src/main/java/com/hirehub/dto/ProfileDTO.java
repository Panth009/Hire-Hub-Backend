package com.hirehub.dto;

import java.util.Base64;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.hirehub.entity.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO
{
	@Id
	private long id;
	private String email;
	private String name;
	private String jobTitle;
	private String company;
	private String location;
	private String about;
	private String picture;
	private Long totalExp;
	private List<String> skills;
	private List<Experience> experiences;
	private List<Certification> certifications;
	private List<Long> savedJobs;
	
	public Profile toEntity() 
	{
		return new Profile(this.id, this.email, this.name, this.jobTitle, this.company, this.location, this.about, this.picture!=null ? Base64.getDecoder().decode(this.picture) : null, this.totalExp, this.skills, this.experiences, this.certifications, this.savedJobs);
	}
}
