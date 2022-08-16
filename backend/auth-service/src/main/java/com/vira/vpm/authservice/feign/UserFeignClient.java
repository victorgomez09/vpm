package com.vira.vpm.authservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vira.vpm.authservice.dto.RegisterDto;

@FeignClient(name = "user-service", path = "/users")
public interface UserFeignClient {

  @PostMapping("/create")
  public ResponseEntity<RegisterDto> save(@RequestBody RegisterDto userData);
}
