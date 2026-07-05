package com.hirehub.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirehub.dto.ApplicantDTO;
import com.hirehub.dto.Application;
import com.hirehub.dto.ApplicationStatus;
import com.hirehub.dto.JobDTO;
import com.hirehub.dto.JobStatus;
import com.hirehub.dto.NotificationDTO;
import com.hirehub.entity.Applicant;
import com.hirehub.entity.Job;
import com.hirehub.exception.HireHubException;
import com.hirehub.repository.JobRepository;
import com.hirehub.utility.Utilities;


@Service("jobService")
public class JobServiceImpl implements JobService
{
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private NotificationService notificationService; 

	@Override
	public JobDTO postJob(JobDTO jobdto) throws HireHubException 
	{
		
		Optional<Job> op = jobRepository.findById(jobdto.getId());
		if(op.isEmpty()) 
		{
			jobdto.setId(Utilities.getSequence("jobs"));
			jobdto.setPostTime(LocalDateTime.now());
			
			NotificationDTO notification = new NotificationDTO();
			notification.setUserId(jobdto.getPostedBy());
			notification.setAction("Job Posted");
			notification.setMessage("Jobs Posted For Role Of " + jobdto.getJobTitle() + " At " + jobdto.getCompany());
			notification.setRoute("/posted-jobs/" + jobdto.getId());
			
			try {
				notificationService.sendNotification(notification);
			} catch (HireHubException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			if(!(op.get().getJobStatus().equals(JobStatus.ACTIVE) && op.get().getJobStatus().equals(jobdto.getJobStatus()))) 
			{
//				op.get().getJobStatus() != JobStatus.ACTIVE || jobdto.getJobStatus() != JobStatus.ACTIVE
				jobdto.setPostTime(LocalDateTime.now());
			}
		}
		
		Job job = jobRepository.save(jobdto.toEntity());
		return job.toDTO();
	}

	@Override
	public List<JobDTO> getAllJobs() 
	{
		return jobRepository.findAll().stream().map((x)->x.toDTO()).toList();
	}

	@Override
	public JobDTO getJob(Long id) throws HireHubException 
	{
		return jobRepository.findById(id).orElseThrow(()-> new HireHubException("JOB_NOT_FOUND")).toDTO();
	}

	@Override
	public void applyJob(Long id, ApplicantDTO applicant) throws HireHubException
	{
		Job job = jobRepository.findById(id).orElseThrow(()->new HireHubException("JOB_NOT_FOUND"));
		
		List<Applicant> applicants = job.getApplicants();
		
		if(applicants==null) 
		{
			applicants = new ArrayList<>();
		}
		
		if(applicants.stream().anyMatch(x->x.getApplicantId() == applicant.getApplicantId())) 
		{
			throw new HireHubException("JOB_ALREADY_APPLIED");
		}
		
		applicant.setApplicationStatus(ApplicationStatus.APPLIED);
		applicants.add(applicant.toEntity());
		
		job.setApplicants(applicants);
		
		jobRepository.save(job);
	}

	@Override
	public List<JobDTO> getJobPostedBy(Long id) throws HireHubException 
	{
		
		return jobRepository.findByPostedBy(id).stream().map((x)->x.toDTO()).toList();
	}

	@Override
	public void changeAppStatus(Application application) throws HireHubException 
	{
		Job job = jobRepository.findById(application.getId()).orElseThrow(()->new HireHubException("JOB_NOT_FOUND"));
		
		List<Applicant> applicants = job.getApplicants().stream().map((x)->{
			if(x.getApplicantId() == application.getApplicantId()) 
			{
				x.setApplicationStatus(application.getApplicationStatus());
				if(x.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING)) 
				{
					x.setInterviewTime(application.getInterviewTime());
					
					NotificationDTO notification = new NotificationDTO();
					notification.setUserId(x.getApplicantId());
					notification.setAction("Interview Schedule");
					notification.setMessage("Interview Schedule For Job Of " + job.getJobTitle() + " At " + job.getCompany());
					notification.setRoute("/job-history");
					try {
						notificationService.sendNotification(notification);
					} catch (HireHubException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			return x;
		}).toList();
		
		job.setApplicants(applicants);
		jobRepository.save(job);
		
	}
}
