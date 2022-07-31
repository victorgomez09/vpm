package com.vira.vpm.users.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.users.model.Project;
import com.vira.vpm.users.model.User;
import com.vira.vpm.users.repository.ProjectRepository;
import com.vira.vpm.users.repository.UserRepository;

@Service
public class ProjectService {
 
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Project> findAllByUser(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        List<Project> projectLists = projectRepository.findByUsers(new HashSet<User>(Arrays.asList(user)));

        return null;
    }
}
