package com.hirehub.entity;

import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.data.mongodb.core.mapping.Document;

import com.hirehub.dto.ApplicantDTO;
import com.hirehub.dto.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("applicant")
public class Applicant
{
	private Long applicantId;
    private String name;
    private String email;
    private Long phone;
    private String website;
    private byte[]  resume;
    private String coverLetter;
    private LocalDateTime timestamp;
    private ApplicationStatus applicationStatus;
    private LocalDateTime interviewTime;
	
	public ApplicantDTO toDTO() 
	{
		return new ApplicantDTO(this.applicantId = applicantId, this.name = name, this.email = email, this.phone = phone, this.website = website, this.resume!=null? Base64.getEncoder().encodeToString(this.resume):null, this.coverLetter = coverLetter, this.timestamp = timestamp, this.applicationStatus = applicationStatus, this.interviewTime = interviewTime);
	}
}
