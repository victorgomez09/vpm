package com.vira.vpm.users.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.vira.vpm.users.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
