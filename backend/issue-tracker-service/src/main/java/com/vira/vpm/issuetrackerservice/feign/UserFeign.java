package com.vira.vpm.issuetrackerservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vira.vpm.issuetrackerservice.dto.UserDto;

@FeignClient(name = "user-service", path = "/users")
public interface UserFeign {

    @GetMapping
    public List<UserDto> findAllUsersByIds(@RequestParam("users") List<String> usersList);
}
