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

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Authentication", description = "Sign in")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    
    private ApplicationUserDetailsService applicationUserDetailsService;
    
    @Autowired
    private JwtUtil jwtTokenProvider;

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
    
    // [ ] Catcher l'execption si l'utilisateur n'existe pas ou si le mot de passe est incorrect ou si le token n'est pas valide(403) avec un message d'erreur
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
