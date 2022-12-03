package TA_C_SHA_90.RumahSehatAPI.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private PasienRestService pasienRestService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		PasienModel pasien = pasienRestService.getPasienByUsername(username);
		if (pasien == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new User(pasien.getUsername(), pasien.getPassword(), new ArrayList<>());
	}
	
	public String encrypt(String password){
        String hashedPassword = bcryptEncoder.encode(password);
        return hashedPassword;
    }
}