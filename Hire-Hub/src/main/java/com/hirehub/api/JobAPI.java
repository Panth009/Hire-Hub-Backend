package com.hirehub.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.dto.ApplicantDTO;
import com.hirehub.dto.Application;
import com.hirehub.dto.JobDTO;
import com.hirehub.dto.ResponseDTO;
import com.hirehub.entity.Job;
import com.hirehub.exception.HireHubException;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.JobService;
import com.hirehub.utility.Utilities;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/jobs")
public class JobAPI 
{

    private final UserRepository userRepository;
	
    @Autowired
	JobService jobService;

    JobAPI(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@PostMapping("/post")
	public ResponseEntity<JobDTO> postJob(@RequestBody @Valid JobDTO jobdto) throws HireHubException 
	{
		return new ResponseEntity<>(jobService.postJob(jobdto),HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllJob")
	public ResponseEntity<List<JobDTO>> getAllJobs()
	{
		return new ResponseEntity<>(jobService.getAllJobs(),HttpStatus.OK);
	}
	
	@GetMapping("/getJob/{id}")
	public ResponseEntity<JobDTO> getJob(@PathVariable Long id) throws HireHubException 
	{
		return new ResponseEntity<>(jobService.getJob(id),HttpStatus.OK);
	}
	
	@PostMapping("/apply/{id}")
	public ResponseEntity<ResponseDTO> applyJob(@PathVariable Long id, @RequestBody ApplicantDTO applicant) throws HireHubException
	{
		jobService.applyJob(id, applicant);
		return new ResponseEntity<>(new ResponseDTO("Applied Successfully...!"), HttpStatus.OK);
	}
	
	@GetMapping("/postedBy/{id}")
	public ResponseEntity<List<JobDTO>> getJobPostedBy(@PathVariable Long id) throws HireHubException
	{
		return new ResponseEntity<>(jobService.getJobPostedBy(id),HttpStatus.OK);
	}
	
	@PostMapping("/changeAppStatus")
	public ResponseEntity<ResponseDTO> changeAppStatus(@RequestBody Application application) throws HireHubException
	{
		jobService.changeAppStatus(application);
		return new ResponseEntity<>(new ResponseDTO("Application Changed Successfully...!"),HttpStatus.OK);
	}
}
