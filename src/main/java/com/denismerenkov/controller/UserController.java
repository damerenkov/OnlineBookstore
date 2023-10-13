package com.denismerenkov.controller;

import com.denismerenkov.dto.ResponseResult;
import com.denismerenkov.model.user.User;
import com.denismerenkov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<User>> add(@RequestBody User user){
        try {
            this.userService.register(user);
            return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseResult<User>> getUserById(@PathVariable long id){
        try {
            User user = userService.findById(id);
            return new ResponseEntity<>(
                    new ResponseResult<>(null, user), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    //TODO make password change
}
