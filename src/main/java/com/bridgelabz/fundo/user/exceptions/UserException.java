package com.bridgelabz.fundo.user.exceptions;

public class UserException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	int code;
	String message;

	public UserException(String message) {
		super(message);
	}
	public UserException( String message,int code )
	{
		super(message);
		this.code=code;
	}
}
