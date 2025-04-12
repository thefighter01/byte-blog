package com.fighter.byteblog.controllers;


import com.fighter.byteblog.domain.dto.AuthResponseDto;
import com.fighter.byteblog.domain.dto.LoginRequestDto;
import com.fighter.byteblog.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
         UserDetails user = authService.authenticate(loginRequestDto.getEmail(), loginRequestDto.getPassword());

         String tokenValue = authService.generateToken(user);

        AuthResponseDto authResponseDto = AuthResponseDto.builder()
                .token(tokenValue)
                .expiresIn(86400000).build(); // in mss
        return ResponseEntity.ok(authResponseDto);
    }
}
