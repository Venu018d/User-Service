package com.zmater.userservice.dto;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PhoneNumberDTO {
	
	@NotBlank
	@Digits(message="Number should only contain 10 digits. It should contain any characters", fraction = 0, integer = 10)
	@Size(min=10,max=10)
	private String phoneNumber;
	
	public PhoneNumberDTO()
	{
		
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "SignupDTO [phoneNumber=" + phoneNumber + "]";
	}
	
	

}
