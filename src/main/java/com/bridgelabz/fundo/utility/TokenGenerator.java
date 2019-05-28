package com.bridgelabz.fundo.utility;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
@Component
public class TokenGenerator {
	private static String Token = "ssbo";

	public String generateToken(long id) throws UnsupportedEncodingException {
		Algorithm algorithm = null;

		try {
			algorithm = Algorithm.HMAC256(Token);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		String token = JWT.create().withClaim("srujan", id).sign(algorithm);
		System.out.println(token);

		return token;
	}

	public long decodeToken(String token) {
		System.out.println("in starting");
		Verification verify = JWT.require(Algorithm.HMAC256(Token));
		JWTVerifier jwtverifier = verify.build();
		System.out.println("in middle");
		DecodedJWT decodejwt = jwtverifier.verify(token);
		Claim claim = decodejwt.getClaim("srujan");
		long userId = claim.asLong();
		System.out.println(userId);
		System.out.println("userid printed");
		return userId;
	}
}
