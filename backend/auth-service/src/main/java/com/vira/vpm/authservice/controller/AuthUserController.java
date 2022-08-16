package com.vira.vpm.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vira.vpm.authservice.dto.LoginDto;
import com.vira.vpm.authservice.dto.RegisterDto;
import com.vira.vpm.authservice.dto.TokenDto;
import com.vira.vpm.authservice.entity.AuthUser;
import com.vira.vpm.authservice.service.AuthUserService;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

  @Autowired
  private AuthUserService authUserService;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
    TokenDto tokenDto = authUserService.login(loginDto);
    if (tokenDto == null)
      return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(tokenDto);
  }

  @PostMapping("/validate")
  public ResponseEntity<TokenDto> validate(@RequestParam String token) {
    TokenDto tokenDto = authUserService.validate(token);
    if (tokenDto == null)
      return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(tokenDto);
  }

  @PostMapping("/create")
  public ResponseEntity<AuthUser> create(@RequestBody RegisterDto registerDto) {
    AuthUser authUser = authUserService.save(registerDto);
    if (authUser == null)
      return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(authUser);
  }
}