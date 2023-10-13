package com.denismerenkov.controller;


import com.denismerenkov.dto.ResponseResult;
import com.denismerenkov.model.Book;
import com.denismerenkov.service.BookService;
import com.denismerenkov.util.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping("/admin")
    public ResponseEntity<ResponseResult<Book>> addBook(@RequestBody Book book) {
        try {
            this.bookService.add(book);
            return new ResponseEntity<>(new ResponseResult<>(null, book), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/admin")
    public ResponseEntity<ResponseResult<Book>> updateBook(@RequestBody Book book) {
        try {
            Book update = this.bookService.update(book);
            return new ResponseEntity<>(new ResponseResult<>(null, update), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ResponseResult<Book>> deleteBook(@PathVariable long id) {
        try {
            Book book = this.bookService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, book), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Book>>> get() {
        try {
            List<Book> books = this.bookService.get();
            return new ResponseEntity<>(new ResponseResult<>(null, books), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/title")
    public ResponseEntity<ResponseResult<List<Book>>> getByTitle(@RequestParam String title) {
        try {
            List<Book> books = this.bookService.getByTitle(title);
            return new ResponseEntity<>(new ResponseResult<>(null, books), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/author")
    public ResponseEntity<ResponseResult<List<Book>>> getByAuthor(@RequestParam String author) {
        try {
            List<Book> books = this.bookService.getByAuthor(author);
            return new ResponseEntity<>(new ResponseResult<>(null, books), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/genre")
    public ResponseEntity<ResponseResult<List<Book>>> getByGenre(@RequestParam String genre) {
        try {
            List<Book> books = this.bookService.getByGenre(genre);
            return new ResponseEntity<>(new ResponseResult<>(null, books), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sort")
    public ResponseEntity<ResponseResult<List<Book>>> getSorted(@RequestParam SortType sortType) {
        try {
            List<Book> books = this.bookService.sort(sortType);
            return new ResponseEntity<>(new ResponseResult<>(null, books), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
