package oth.sid.models;

import java.io.Serializable;

public class AuthentificationResponse implements Serializable{
	
	private final String jwt;

	public String getJwt() {
		return jwt;
	}

	public AuthentificationResponse(String jwt) {
		this.jwt = jwt;
	}
	
	

}
