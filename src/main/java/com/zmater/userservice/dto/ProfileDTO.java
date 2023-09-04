package com.zmater.userservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern.Flag;

public class ProfileDTO {
	
	@NotBlank
   private String name;
	@NotBlank
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Flag.CASE_INSENSITIVE,message = "Email is Not Valid")
   private String email;
	
   private String referralCode;
   
   public ProfileDTO()
   {
	   
   } 
	public ProfileDTO(String name, String email, String referralCode) {
		super();
		this.name = name;
		this.email = email;
		this.referralCode = referralCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReferralCode() {
		return referralCode;
	}
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}
}
