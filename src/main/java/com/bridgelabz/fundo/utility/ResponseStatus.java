package com.bridgelabz.fundo.utility;

import com.bridgelabz.fundo.response.Response;
import com.bridgelabz.fundo.response.ResponseToken;

public class ResponseStatus {
	

	public static Response sendResponse(String statusMessage,int statusCode)
	{
		Response response= new Response();
		response.setStatusMessage(statusMessage);
		response.setStatusCode(statusCode);
		return response;
	}

	public static ResponseToken statusTokenResponse(String statusMessage,int statusCode)
	{
		ResponseToken responseToken=new ResponseToken();
		responseToken.setStatusCode(statusCode);
		responseToken.setStatusMessage(statusMessage);
		return responseToken;
	}
	public static ResponseToken tokenStatus(String statusMessage,String token ,int statusCode)
	{
		ResponseToken responseToken=new ResponseToken();
		responseToken.setStatusCode(statusCode);
		responseToken.setStatusMessage(statusMessage);
		responseToken.setToken(token);
				
		return responseToken;
	}

}
