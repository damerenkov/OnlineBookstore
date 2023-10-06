package com.denismerenkov.controller;

import com.denismerenkov.dto.ResponseResult;
import com.denismerenkov.model.user.Role;
import com.denismerenkov.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<Role>> add(@RequestBody Role role){
        try {
            this.roleService.add(role);
            return new ResponseEntity<>(new ResponseResult<>(null, role), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<ResponseResult<Role>> addAdmin(){
        return this.add(new Role("ROLE_ADMIN"));
    }
}
