package com.denismerenkov.service;

import com.denismerenkov.model.Book;
import com.denismerenkov.repository.BookRepository;
import com.denismerenkov.util.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Book> getByTitle(String title) {
        return this.bookRepository.getBooksByTitle(title);
    }

    @Override
    public List<Book> getByAuthor(String author) {
        return this.bookRepository.getBooksByAuthor(author);
    }

    @Override
    public List<Book> getByGenre(String genre) {
        return this.bookRepository.getBooksByGenre(genre);
    }

    @Override
    public List<Book> sort(SortType type) {
        List<Book> books = this.get();
        switch (type){
            case GENRE:
                return books.stream().sorted((Comparator.comparing(Book::getGenre))).collect(Collectors.toList());
            case PRICE:
                return books.stream().sorted(Comparator.comparing(Book::getPrice)).collect(Collectors.toList());
            default:
                return books;
        }
    }

    @Override
    public Book get(long id) {
        return this.bookRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No such book")
        );
    }

    @Override
    public Book update(Book book) {
        if(book.getQuantityInStock() == 0){
            throw new IllegalArgumentException("This book is out of stock!");
        }
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
