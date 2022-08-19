package com.vira.vpm.authservice.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vira.vpm.authservice.dto.LoginDto;
import com.vira.vpm.authservice.dto.RegisterDto;
import com.vira.vpm.authservice.dto.TokenDto;
import com.vira.vpm.authservice.dto.UserDto;
import com.vira.vpm.authservice.service.AuthUserService;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

  @Autowired
  private AuthUserService authUserService;

  @GetMapping("/get-user")
  public ResponseEntity<UserDto> getUser(@RequestParam("token") String token) {
    UserDto user = authUserService.getUser(token);
    if (user == null)
      return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(user);
  }
  
  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse) {
    TokenDto tokenDto = authUserService.login(loginDto);
    if (tokenDto == null)
      return ResponseEntity.badRequest().build();

    // create cookie for refresh token
    // Cookie refreshTokenCookie = new Cookie("token", tokenDto.getToken());
    // refreshTokenCookie.setHttpOnly(true);
    // refreshTokenCookie.setSecure(false); // only allows HTTPS
    // refreshTokenCookie.setPath("/");
    // refreshTokenCookie.setDomain("api.lsp.com"); // restrict domain
    // httpServletResponse.addCookie(refreshTokenCookie);

    return ResponseEntity.ok(tokenDto);
  }

  @PostMapping("/validate")
  public ResponseEntity<TokenDto> validate(@RequestParam("token") String token) {
    TokenDto tokenResponse = authUserService.validate(token);
    if (tokenResponse == null)
      return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(tokenResponse);
  }

  @PostMapping("/create")
  public ResponseEntity<RegisterDto> create(@RequestBody RegisterDto registerDto) {
    RegisterDto registeredUser = authUserService.save(registerDto);
    if (registeredUser == null)
      return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(registeredUser);
  }
}