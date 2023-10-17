package com.rocketseatviny.todolist.controller;

import com.rocketseatviny.todolist.domain.UserModel;
import com.rocketseatviny.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserModel> create(@RequestBody UserModel userModel) {
        UserModel createdUser = userService.create(userModel);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/findByUsername")
    public ResponseEntity<UserModel> findByUsername(@RequestParam String username) {
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping(path = "/findUser/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @GetMapping(path = "/findAllUsers")
    public ResponseEntity<List<UserModel>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
