package com.denismerenkov.controller;

import com.denismerenkov.dto.ResponseResult;
import com.denismerenkov.model.user.User;
import com.denismerenkov.security.jwt.JwtTokenProvider;
import com.denismerenkov.service.UserService;
import com.denismerenkov.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;


    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<Map<String, String>>> login(@RequestParam String userName, @RequestParam String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            User user = userService.findByUsername(userName);

            String token = jwtTokenProvider.createToken(userName, user.getRoles());

            String hash = StringUtil.generateHash();
            user.setHash(hash);
            this.userService.update(user);


            Map<String, String> response = new HashMap<>();
            response.put("username", userName);
            response.put("token", token);
            response.put("hash", hash);

            return new ResponseEntity<>(new ResponseResult<>(null, response), HttpStatus.OK);
        } catch (AuthenticationException e) {
             throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping
    public ResponseEntity<ResponseResult<Map<String, String>>> refresh(@RequestParam String hash){
        try {
            User user = this.userService.findByHash(hash);

            String token = jwtTokenProvider.createToken(user.getUserName(), user.getRoles());

            Map<String, String> response = new HashMap<>();
            response.put("username", user.getUserName());
            response.put("token", token);

            return new ResponseEntity<>(new ResponseResult<>(null, response), HttpStatus.OK);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }


}
