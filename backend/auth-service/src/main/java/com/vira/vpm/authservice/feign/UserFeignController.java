package com.vira.vpm.authservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.vira.vpm.authservice.dto.RegisterDto;

@FeignClient(name = "user-service", path = "/users")
public interface UserFeignController {

  @PostMapping("/create")
  public ResponseEntity<RegisterDto> save(@RequestBody RegisterDto userData);
}
