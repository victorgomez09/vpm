package com.vira.vpm.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vira.vpm.authservice.dto.LoginDto;
import com.vira.vpm.authservice.dto.RegisterDto;
import com.vira.vpm.authservice.dto.TokenDto;
import com.vira.vpm.authservice.entity.AuthUser;
import com.vira.vpm.authservice.repository.AuthUserRepository;
import com.vira.vpm.authservice.security.JwtProvider;
import com.vira.vpm.authservice.feign.UserFeignClient;

@Service
public class AuthUserService {

  @Autowired
  private AuthUserRepository authUserRepository;
  @Autowired
  private JwtProvider jwtProvider;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserFeignClient userFeignController;

  public RegisterDto save(RegisterDto registerDto) {
    AuthUser user = authUserRepository.findByEmail(registerDto.getEmail());
    if (user != null)
      return null;
    userFeignController.save(registerDto);
    String password = passwordEncoder.encode(registerDto.getPassword());
    AuthUser authUser = AuthUser.builder()
        .email(registerDto.getEmail())
        .password(password)
        .build();
    authUserRepository.save(authUser);
    return registerDto;
  }

  public TokenDto login(LoginDto loginDto) {
    AuthUser user = authUserRepository.findByEmail(loginDto.getEmail());
    if (user == null)
      return null;
    if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
      return new TokenDto(jwtProvider.createToken(user));
    return null;
  }

  public TokenDto validate(String token) {
    if (!jwtProvider.validate(token))
      return null;
    String email = jwtProvider.getUserEmailFromToken(token);
    if (authUserRepository.findByEmail(email) == null)
      return null;
    return new TokenDto(token);
  }
}
