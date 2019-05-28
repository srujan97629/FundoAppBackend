package com.bridgelabz.fundo.user.dto;

public class ForgotPasswordDto {
	
	private String newPassword;
	private String confirnPassword;

	public ForgotPasswordDto() {

	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirnPassword() {
		return confirnPassword;
	}

	public void setConfirnPassword(String confirnPassword) {
		this.confirnPassword = confirnPassword;
	}

	@Override
	public String toString() {
		return "ForgotPasswordDto [newPassword=" + newPassword + ", confirnPassword=" + confirnPassword + "]";
	}

}
