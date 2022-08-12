package com.vira.vpm.project.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vira.vpm.project.dto.UserDto;

@FeignClient(name = "user-service", path = "/users")
public interface UsersFeignClient {
    
    @GetMapping("/list/{usersList}")
    public List<UserDto> findAllUsersByIds(@RequestParam("usersList") List<String> usersList);
}
