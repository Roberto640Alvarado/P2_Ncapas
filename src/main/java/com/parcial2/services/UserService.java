package com.parcial2.services;

import java.util.List;
import java.util.UUID;

import com.parcial2.models.dtos.PasswordDTO;
import com.parcial2.models.dtos.SaveDTO;
import com.parcial2.models.entities.User;

public interface UserService {
    void save(SaveDTO info) throws Exception;
    void deleteById(String id) throws Exception;
    void changePassword(UUID id, PasswordDTO info)throws Exception;
    User findOneById(UUID id);
    List<User> findAll();
}
