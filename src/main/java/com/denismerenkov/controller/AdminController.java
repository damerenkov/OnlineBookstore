package com.denismerenkov.controller;

import com.denismerenkov.model.user.User;
import com.denismerenkov.service.BookService;
import com.denismerenkov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final BookService bookService;

    public AdminController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    //TODO make add, update, delete for books
}
