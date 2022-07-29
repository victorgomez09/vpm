package com.vira.vpm.users.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.users.dto.UserDto;
import com.vira.vpm.users.model.User;
import com.vira.vpm.users.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> findAll() {
        Iterable<User> userIterable = userRepository.findAll();
        return StreamSupport.stream(userIterable.spliterator(), false).map((User user) -> new UserDto(user.getId(), user.getUsername(), user.getEmail(),
            user.getFullname(), user.getRole().getName(), user.getActive(), user.getLastLogin(), user.getProjects(),
            user.getCreationDate(), user.getUpdateDate())).collect(Collectors.toList());
    }
    
}
