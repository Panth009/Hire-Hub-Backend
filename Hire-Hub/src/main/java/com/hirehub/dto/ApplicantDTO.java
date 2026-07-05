package com.hirehub.dto;

import java.time.LocalDateTime;
import java.util.Base64;

import com.hirehub.entity.Applicant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicantDTO
{
	private Long applicantId;
    private String name;
    private String email;
    private Long phone;
    private String website;
    private String resume;
    private String coverLetter;
    private LocalDateTime timestamp;
    private ApplicationStatus applicationStatus;
    private LocalDateTime interviewTime;
	
	public Applicant toEntity() 
	{
		return new Applicant(this.applicantId = applicantId, this.name = name, this.email = email, this.phone = phone, this.website = website, this.resume!=null? Base64.getDecoder().decode(this.resume):null, this.coverLetter = coverLetter, this.timestamp = timestamp, this.applicationStatus = applicationStatus, this.interviewTime = interviewTime);
	}
}
