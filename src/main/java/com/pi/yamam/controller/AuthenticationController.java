package com.pi.yamam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.yamam.domain.user.AuthenticationDTO;
import com.pi.yamam.domain.user.LoginResponseDTO;
import com.pi.yamam.domain.user.RegisterDTO;
import com.pi.yamam.domain.user.User;
import com.pi.yamam.infra.security.TokenService;
import com.pi.yamam.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/teste")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        User user = userRepository.findByEmail(data.email()).orElseThrow(() -> new RuntimeException("User not found!"));

        
        if (passwordEncoder.matches(user.getPassword(), data.password())) {
            System.out.println("passei aqui");
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.ok("teste");
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
      

        String passEncripted = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.cpf(), data.email(), passEncripted, data.userStatus(), data.role());

        this.userRepository.save(newUser);
        return ResponseEntity.ok(newUser);

    }
}
