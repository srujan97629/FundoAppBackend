package com.bridgelabz.fundo.response;

public class Response {
	private int statusCode;
	private String statusMessage;
	
	public Response() {
		
	}

	public Response(int statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public String toString() {
		return "Responce [statusCode=" + statusCode + ", statusMessage=" + statusMessage + "]";
	}
	
	

}
