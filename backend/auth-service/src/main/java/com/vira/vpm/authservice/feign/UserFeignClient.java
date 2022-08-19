package com.vira.vpm.authservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.vira.vpm.authservice.dto.RegisterDto;
import com.vira.vpm.authservice.dto.UserDto;

@FeignClient(name = "user-service", path = "/users")
public interface UserFeignClient {

  @PostMapping("/create")
  public ResponseEntity<RegisterDto> save(@RequestBody RegisterDto userData);

  @GetMapping("/find")
  public ResponseEntity<UserDto> findByEmail(@RequestParam("email") String email);
}
