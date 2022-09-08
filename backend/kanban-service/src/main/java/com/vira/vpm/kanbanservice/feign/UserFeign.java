package com.vira.vpm.kanbanservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vira.vpm.kanbanservice.dto.UserDto;


@FeignClient(name = "user-service", path = "/users")
public interface UserFeign {
    
    @GetMapping("/list")
    public List<UserDto> findAllUsersByIds(@RequestParam("usersList") List<String> usersList);
}
