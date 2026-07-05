package com.hirehub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirehub.dto.ApplicantDTO;
import com.hirehub.dto.Application;
import com.hirehub.dto.JobDTO;
import com.hirehub.exception.HireHubException;



@Service
public interface JobService
{

	public JobDTO postJob(JobDTO jobdto) throws HireHubException;

	public List<JobDTO> getAllJobs();

	public JobDTO getJob(Long id) throws HireHubException;

	public void applyJob(Long id, ApplicantDTO applicant) throws HireHubException;

	public List<JobDTO> getJobPostedBy(Long id) throws HireHubException;

	public void changeAppStatus(Application application) throws HireHubException; 

}
