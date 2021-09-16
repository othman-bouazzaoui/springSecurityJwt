package oth.sid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import oth.sid.models.AuthentificationRequest;
import oth.sid.models.AuthentificationResponse;
import oth.sid.service.MyUserDetailsService;
import oth.sid.util.JwtUtil;

@RestController
public class HelloRessource {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@RequestMapping(value="/hello", method = RequestMethod.GET)
	public String hello() {
		return "hello Othman";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAutheticationToken(@RequestBody AuthentificationRequest authentificationRequest) throws Exception{
		try {
		authenticationManager.authenticate(	new UsernamePasswordAuthenticationToken(authentificationRequest.getUsername(), authentificationRequest.getPassword()));
		}catch (BadCredentialsException e) {
			throw new Error("Bad infos ");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authentificationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		System.out.println(jwt);
		return ResponseEntity.ok(new AuthentificationResponse(jwt));
	}

}
