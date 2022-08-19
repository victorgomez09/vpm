package com.vira.vpm.users.service;

import java.util.List;
import java.util.stream.Collectors;

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
        return userRepository.findAll().stream().map((User user) -> new UserDto(user.getId(), user.getEmail(),
                user.getFullname(), user.getCreationDate(), user.getUpdateDate())).collect(Collectors.toList());
    }

    public UserDto findById(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new UserDto(user.getId(), user.getEmail(), user.getFullname(),
                    user.getCreationDate(), user.getUpdateDate());
        }
        return null;
    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return null;
        return new UserDto(user.getId(), user.getEmail(), user.getFullname(), user.getCreationDate(), user.getUpdateDate());
    }

    public UserDto save(UserDto userData) {
        User userToCreate = userRepository.findByEmail(userData.getEmail());
        if (userToCreate == null) {
            userToCreate = userRepository
                    .save(new User(userData.getEmail(), userData.getFullname()));
            return new UserDto(userToCreate.getId(), userToCreate.getEmail(), userToCreate.getFullname(),
                    userToCreate.getCreationDate(), userToCreate.getUpdateDate());
        }
        return null;
    }

    public UserDto update(UserDto userData) {
        User userToUpdate = userRepository.findById(userData.getId()).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setFullname(userData.getFullname());
            userToUpdate.setEmail(userData.getEmail());
            userRepository.save(userToUpdate);
            return new UserDto(userToUpdate.getId(), userToUpdate.getEmail(), userToUpdate.getFullname(),
                    userToUpdate.getCreationDate(), userToUpdate.getUpdateDate());
        }
        return null;
    }

    public void delete(String userId) {
        userRepository.deleteById(userId);
    }
}
