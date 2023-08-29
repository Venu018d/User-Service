package com.zmater.userservice.dto;

public class UserDTO {
	
	private String phoneNumber;
	private String name;
	private String email;
	private String referralCode;	
	
	public UserDTO(String phoneNumber, String name, String email, String referralCode) {
		super();
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.email = email;
		this.referralCode = referralCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	@Override
	public String toString() {
		return "UserDTO [phoneNumber=" + phoneNumber + ", name=" + name + ", email=" + email + ", referralCode="
				+ referralCode + "]";
	}
	
	

}
