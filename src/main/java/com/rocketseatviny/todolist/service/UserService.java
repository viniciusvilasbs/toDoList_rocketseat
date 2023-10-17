package com.rocketseatviny.todolist.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.rocketseatviny.todolist.domain.UserModel;
import com.rocketseatviny.todolist.exception.BadRequestException;
import com.rocketseatviny.todolist.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserModel create(@RequestBody UserModel userModel) {
        UserModel registeredUser = findByUsername(userModel.getUsername());

        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);

        if(registeredUser != null) {
            throw new BadRequestException("Username de usuário já cadastrado!");
        }
        return userRepository.save(userModel);
    }

    public UserModel findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public UserModel findByIdOrThrowBadRequestException(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuário não encontrado!"));
    }

    public List<UserModel> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(findByIdOrThrowBadRequestException(id));
    }
}
