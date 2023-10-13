package com.denismerenkov.service;

import com.denismerenkov.model.Book;
import com.denismerenkov.util.SortType;

import java.util.List;

public interface BookService {

    boolean add(Book book);
    List<Book> get();
    List<Book> getByTitle(String title);
    List<Book> getByAuthor(String author);
    List<Book> getByGenre(String genre);
    List<Book> sort(SortType type);
    Book get(long id);
    Book update(Book book);
    Book delete(long id);

}
