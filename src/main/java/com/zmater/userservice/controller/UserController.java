package com.zmater.userservice.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zmater.userservice.dto.PhoneNumberDTO;
import com.zmater.userservice.dto.ProfileDTO;
import com.zmater.userservice.dto.VehicleDTO;
import com.zmater.userservice.entity.User;
import com.zmater.userservice.entity.Vehicle;
import com.zmater.userservice.exception.UnauthorizedException;
import com.zmater.userservice.exception.UserNotFoundException;
import com.zmater.userservice.exception.VehicleNotFoundException;
import com.zmater.userservice.repository.UserRepository;
import com.zmater.userservice.repository.VehicleRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup( @Valid @RequestBody  PhoneNumberDTO phoneNumberDTO)
	{	
		User user=new User();
		user.setPhoneNumber(phoneNumberDTO.getPhoneNumber());
		 User savedUser=repository.save(user);
		 URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/signup").replacePath("/users/{id}/profile").buildAndExpand(savedUser.getId()).toUri();	
		//URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/login")
	public User login(@Valid @RequestBody PhoneNumberDTO phoneNumberDTO) throws UserNotFoundException
	{
		User user=repository.findByPhoneNumber(phoneNumberDTO.getPhoneNumber());
		if(user==null)
			throw new  UserNotFoundException("User Not Found");
		
		return user;
			
	}
	
    @GetMapping("/{userId}/profile")
    public User getUserProfile(@PathVariable int userId) throws UserNotFoundException
    {
    	User user=repository.findById(userId).orElse(null);
    	if(user==null)
    		throw new UserNotFoundException("User Not Found");
    	
    	return user;
    }
    
    
    @PutMapping("/{userId}/profile")
    public ResponseEntity<String> updateProfile(@PathVariable int userId,@Valid @RequestBody ProfileDTO profile) throws UserNotFoundException
    {
    	User user=repository.findById(userId).orElse(null);
    	if(user==null)
    		throw new UserNotFoundException("User Doesn't Exist, Sign Up First!");
    	user.setName(profile.getName());
    	user.setEmail(profile.getEmail());
    	user.setReferralCode(profile.getReferralCode());
    	
    	repository.save(user);
    	
    	return ResponseEntity.ok("Successfully Updated");
    	
    }
    
    @PostMapping("/{userId}/vehicles")
    public void addVehicle(@PathVariable int userId,@Valid @RequestBody VehicleDTO vehicleDTO) throws UserNotFoundException
    {
    	User user=repository.findById(userId).orElse(null);
    	if(user==null)
    		throw new UserNotFoundException("User Doesn't Exist, Sign Up First!");
    	
    	Vehicle vehicle=modelMapper.map(vehicleDTO, Vehicle.class);
    	
        vehicle.setUser(user);
         user.getVehicle().add(vehicle);
         repository.save(user);
               
    }
    
    @PutMapping("/{userId}/vehicles/{vehicleId}")
    public void editVehicle(@PathVariable int userId,@PathVariable int vehicleId,@RequestBody VehicleDTO vehicleDTO) throws UserNotFoundException, VehicleNotFoundException, UnauthorizedException
    {
    	User user=repository.findById(userId).orElse(null);
    	if(user==null)
    		throw new UserNotFoundException("User Doesn't Exist, Sign Up First!");
    	
    	Vehicle savedVehicle=vehicleRepository.findById(vehicleId).orElse(null);
    	if(savedVehicle==null)
    		throw new VehicleNotFoundException("Vehicle doesn't exist");
    	
    	if(savedVehicle.getUser().getId()!=userId)
    	    throw new UnauthorizedException("User is unauthorized to update vehicle");
        
    	modelMapper.map(vehicleDTO, savedVehicle);
    	
    	vehicleRepository.save(savedVehicle);
    	   		
    }
    
    
    @DeleteMapping("/{userId}/vehicles/{vehicleId}")
    public void deleteVehicle(@PathVariable int userId,@PathVariable int vehicleId) throws VehicleNotFoundException, UnauthorizedException
    {
    	
    	Vehicle vehicle=vehicleRepository.findById(vehicleId).orElse(null);

    	if(vehicle==null)
    		throw new VehicleNotFoundException("No such vehicle Exists!");
    	
     	if(vehicle.getUser().getId()!=userId)
	    throw new UnauthorizedException("User is unauthorized to delete vehicle");
       	
    	vehicleRepository.delete(vehicle);
    	
    }
   
}
