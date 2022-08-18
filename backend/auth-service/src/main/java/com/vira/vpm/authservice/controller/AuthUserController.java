package com.vira.vpm.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vira.vpm.authservice.dto.LoginDto;
import com.vira.vpm.authservice.dto.RegisterDto;
import com.vira.vpm.authservice.dto.TokenDto;
import com.vira.vpm.authservice.service.AuthUserService;

@CrossOrigin(origins = "https://victin09-vpm-jwj4xqr63597r-4200.githubpreview.dev")
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
  public ResponseEntity<TokenDto> validate(@RequestParam(name = "token") String token) {
    TokenDto tokenDto = authUserService.validate(token);
    if (tokenDto == null)
      return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(tokenDto);
  }

  @PostMapping("/create")
  public ResponseEntity<RegisterDto> create(@RequestBody RegisterDto registerDto) {
    RegisterDto authUser = authUserService.save(registerDto);
    if (authUser == null)
      return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(authUser);
  }
}