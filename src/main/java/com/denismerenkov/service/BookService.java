package com.denismerenkov.service;

import com.denismerenkov.model.Book;

import java.util.List;

public interface BookService {

    void add(Book book);
    List<Book> get();
    Book get(long id);
    Book update(Book book);
    Book delete(long id);

}
