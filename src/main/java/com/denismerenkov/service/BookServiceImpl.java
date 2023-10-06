package com.denismerenkov.service;

import com.denismerenkov.model.Book;
import com.denismerenkov.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void add(Book book) {
        try {
            this.bookRepository.save(book);
        } catch (Exception e) {
            throw new IllegalArgumentException("Book has already added!");
        }
    }

    @Override
    public List<Book> get() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book get(long id) {
        return this.bookRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No such book")
        );
    }

    @Override
    public Book update(Book book) {
        Book base = this.get(book.getId());
        base.setTitle(book.getTitle());
        base.setAuthor(book.getAuthor());
        base.setGenre(book.getGenre());
        base.setPrice(book.getPrice());
        base.setQuantityInStock(book.getQuantityInStock());

        try {
            return this.bookRepository.save(base);
        } catch (Exception e) {
            throw new IllegalArgumentException("Book is already exists!");
        }
    }

    @Override
    public Book delete(long id) {
        Book book = this.get(id);
        this.bookRepository.delete(book);
        return book;
    }
}
