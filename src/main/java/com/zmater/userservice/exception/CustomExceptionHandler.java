package com.zmater.userservice.exception;


import java.util.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	 protected ResponseEntity<Object> handleMethodArgumentNotValid(
	            MethodArgumentNotValidException ex,
	            HttpHeaders headers, HttpStatus status, WebRequest request) {
	             
		 
		   Map<String,String> errorMap=new HashMap<String, String>();
		 
	        Map<String, Object> responseBody = new LinkedHashMap<>();
	        responseBody.put("timestamp", new Date());
	        responseBody.put("status", status.value());
	         
	        ex.getBindingResult().getFieldErrors().forEach( error -> {
	        	errorMap.put(error.getField(),error.getDefaultMessage());
	        	
	        });
	        		
	         
	        return new ResponseEntity<>(errorMap, headers, status);
	    }

	 
	 @ExceptionHandler(UserNotFoundException.class)  
	 public final ResponseEntity<ExceptionResponse> handleUserNotFoundExceptions(UserNotFoundException ex, WebRequest request)  
     {  
     ExceptionResponse exceptionResponse= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));  
     return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);  
     }     
   
	 @ExceptionHandler(UnauthorizedException.class)  
	 public final ResponseEntity<ExceptionResponse> handleUnathorizedExceptions(UnauthorizedException ex, WebRequest request)  
     {  
     ExceptionResponse exceptionResponse= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));    
     return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);  
     }     
   
	 @ExceptionHandler(VehicleNotFoundException.class)  
	 public final ResponseEntity<ExceptionResponse> handleVehicleNotFoundExceptions(VehicleNotFoundException ex, WebRequest request)  
     {  
     ExceptionResponse exceptionResponse= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));  
     return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);  
     }     
   

	 @ExceptionHandler(UserAlreadyExistsException.class)
     public final ResponseEntity<ExceptionResponse> handleUserAlreadyExists(UserAlreadyExistsException ex, WebRequest request) {	 
     ExceptionResponse error = new ExceptionResponse(new Date(), ex.toString(), ex.getStackTrace().toString());
     return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	 }
		 
	 
	 
     @ExceptionHandler(Exception.class)
     public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {	 
         ExceptionResponse error = new ExceptionResponse(new Date(), ex.toString(), ex.getStackTrace().toString());
         return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
     }
	 
     
}
