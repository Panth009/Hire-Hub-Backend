package com.hirehub.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.hirehub.exception.HireHubException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionControllerAdvice 
{

	@Autowired
	private Environment environmnet;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalException(Exception exception)
	{
		ErrorInfo error = new ErrorInfo(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value(),LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HireHubException.class)
	public ResponseEntity<ErrorInfo> hireHubException(HireHubException exception)
	{
		ErrorInfo error = new ErrorInfo(environmnet.getProperty(exception.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR.value(),LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
	public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception)
	{
		String msg = ""; 
		if(exception instanceof MethodArgumentNotValidException manvexception) 
		{
			msg = manvexception.getAllErrors().stream()
				    .map(error -> error.getDefaultMessage())
				    .collect(Collectors.joining(", "));		}
		else
		{
			ConstraintViolationException cvException = (ConstraintViolationException) exception;
            msg = cvException.getConstraintViolations().stream().map(error -> error.getMessage()).collect(Collectors.joining(", "));
		}
		
		ErrorInfo error = new ErrorInfo(msg, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(error,HttpStatus.BAD_REQUEST);
	}
	
}
