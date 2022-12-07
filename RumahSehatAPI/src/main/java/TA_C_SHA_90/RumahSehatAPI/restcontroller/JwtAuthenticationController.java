package TA_C_SHA_90.RumahSehatAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import TA_C_SHA_90.RumahSehatAPI.security.JwtTokenUtil;
import TA_C_SHA_90.RumahSehatAPI.model.JwtRequestModel;
import TA_C_SHA_90.RumahSehatAPI.model.JwtResponseModel;
import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.service.JwtUserDetailsService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestModel authenticationRequest) throws Exception {
		log.info("Received request at authentication endpoint");
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponseModel(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			log.warn("Unable to authenticate, user has been disabled");
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			log.warn("nable to authenticate, invalid credentials have been provided");
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}