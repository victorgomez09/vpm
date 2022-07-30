package com.vira.vpm.users.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.users.dto.UserDto;
import com.vira.vpm.users.model.Role;
import com.vira.vpm.users.model.User;
import com.vira.vpm.users.repository.RoleRepository;
import com.vira.vpm.users.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map((User user) -> new UserDto(user.getId(), user.getUsername(), user.getEmail(),
            user.getFullname(), user.getRole().getName(), user.getActive(), user.getLastLogin(), user.getProjects(),
            user.getCreationDate(), user.getUpdateDate())).collect(Collectors.toList());
    }
    
    public UserDto findById(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new UserDto(user.getId(), user.getUsername(), user.getEmail(),
                user.getFullname(), user.getRole().getName(), user.getActive(), user.getLastLogin(), user.getProjects(),
                user.getCreationDate(), user.getUpdateDate());
        }
        return null;
    }

    public UserDto save(UserDto userData) {
        User userToCreate = userRepository.findByUsername(userData.getUsername());
        if (userToCreate != null) {
            Role role = roleRepository.findByName(userData.getRole());
            userToCreate = userRepository.save(new User(userData.getUsername(), userData.getEmail(), userData.getFullname(), userData.getPassword(), role, 
                false, null));
            return new UserDto(userToCreate.getId(), userToCreate.getUsername(), userToCreate.getEmail(), userToCreate.getFullname(), userToCreate.getRole().getName(), 
                userToCreate.getActive(), userToCreate.getLastLogin(), userToCreate.getProjects(), userToCreate.getCreationDate(), userToCreate.getUpdateDate());
        }
        return null;
    }

    public UserDto update(UserDto userData) {
        User userToUpdate = userRepository.findById(userData.getId()).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setFullname(userData.getFullname());
            userToUpdate.setEmail(userData.getEmail());
            userToUpdate.setPassword(userData.getPassword());
            userToUpdate.setRole(roleRepository.findByName(userData.getRole()));
            userRepository.save(userToUpdate);
            return new UserDto(userToUpdate.getId(), userToUpdate.getUsername(), userToUpdate.getEmail(), userToUpdate.getFullname(), userToUpdate.getRole().getName(), 
                userToUpdate.getActive(), userToUpdate.getLastLogin(), userToUpdate.getProjects(), userToUpdate.getCreationDate(), userToUpdate.getUpdateDate());
        }
        return null;
    }

    public void delete(String userId) {
        userRepository.deleteById(userId);
    }
}
