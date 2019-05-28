package com.bridgelabz.fundo.user.dto;

public class UserDto {

	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String emailId;
	private String password;

	public UserDto() {

	}

	public UserDto(String firstName, String lastName, String mobileNumber, String emailId, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDto [firstName=" + firstName + ", lastName=" + lastName + ", mobileNumber=" + mobileNumber + ", emailId="
				+ emailId + ", password=" + password + "]";
	}

}
