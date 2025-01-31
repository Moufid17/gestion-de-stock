package com.tmdigital.gestiondestock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.Utils.JwtUtil;
import com.tmdigital.gestiondestock.dto.auth.AuthenticationRequest;
import com.tmdigital.gestiondestock.dto.auth.AuthenticationResponse;
import com.tmdigital.gestiondestock.services.auth.ApplicationUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtTokenProvider;
    
    private ApplicationUserDetailsService applicationUserDetailsService;


	public AuthenticationController(AuthenticationManager authenticationManager, ApplicationUserDetailsService applicationUserDetailsService) {
		this.authenticationManager = authenticationManager;
		this.applicationUserDetailsService = applicationUserDetailsService;
	}
    // 
    // @Autowired
    // private PasswordEncoder passwordEncoder;
    
    // @PostMapping("/register")
    // public ResponseEntity<?> register(@RequestBody AuthenticationRequest registerRequest) {
    //     User user = new User();
    //     user.setEmail(registerRequest.getLogin());
    //     // user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    //     user.setPassword(registerRequest.getPassword());
    //     user.setRules(Arrays.asList(new Roles("USER")));
    //     UserRepository.save(user);
    //     return ResponseEntity.ok("User registered successfully!");
    // }
    
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest req) {

        final UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(req.getLogin());

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getLogin(), req.getPassword())
        );

        final String token = jwtTokenProvider.generateToken(userDetails);

        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(token).build());
    }

}
