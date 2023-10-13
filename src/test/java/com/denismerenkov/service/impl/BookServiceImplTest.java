package com.denismerenkov.service.impl;

import com.denismerenkov.model.Book;
import com.denismerenkov.repository.BookRepository;
import com.denismerenkov.service.BookService;
import com.denismerenkov.util.SortType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void add() {
        Book book = new Book("title", "author", "genre", 100.0, 15);
        boolean add = this.bookService.add(book);
        assertTrue(add);
    }


    @Test
    void get() {
        Book book1 = new Book("title1", "author1", "genre1", 100.0, 15);
        Book book2 = new Book("title2", "author2", "genre1", 1100.0, 125);
        Book book3 = new Book("title3", "author3", "genre2", 1200.0, 135);
        Mockito.when(bookRepository.findAll()).thenReturn(List.of(book1, book2, book3));
        List<Book> expected = new ArrayList<>();
        expected.add(book1);
        expected.add(book2);
        expected.add(book3);

        List<Book> all = this.bookService.get();
        Assert.assertArrayEquals(expected.toArray(), all.toArray());
        Mockito.verify(bookRepository).findAll();
    }

    @Test
    void getByTitle() {
        Book book1 = new Book("title", "author1", "genre1", 100.0, 15);
        Book book2 = new Book("title", "author2", "genre1", 1100.0, 125);
        Mockito.when(bookRepository.getBooksByTitle("title")).thenReturn(List.of(book1, book2));
        List<Book> expected = new ArrayList<>();
        expected.add(book1);
        expected.add(book2);


        Assert.assertArrayEquals(expected.toArray(), this.bookService.getByTitle("title").toArray());
        Mockito.verify(bookRepository).getBooksByTitle("title");

    }

    @Test
    void getByAuthor() {
        Book book2 = new Book("title", "author2", "genre1", 1100.0, 125);
        Book book3 = new Book("title3", "author2", "genre2", 1200.0, 135);
        Mockito.when(bookRepository.getBooksByAuthor("author2")).thenReturn(List.of(book2, book3));
        List<Book> expected = new ArrayList<>();
        expected.add(book2);
        expected.add(book3);


        Assert.assertArrayEquals(expected.toArray(), this.bookService.getByAuthor("author2").toArray());
        Mockito.verify(bookRepository).getBooksByAuthor("author2");

    }

    @Test
    void getByGenre() {
        Book book1 = new Book("title", "author1", "genre1", 100.0, 15);
        Book book3 = new Book("title3", "author2", "genre1", 1200.0, 135);
        Mockito.when(bookRepository.getBooksByGenre("genre1")).thenReturn(List.of(book1, book3));
        List<Book> expected = new ArrayList<>();
        expected.add(book1);
        expected.add(book3);


        Assert.assertArrayEquals(expected.toArray(), this.bookService.getByGenre("genre1").toArray());
        Mockito.verify(bookRepository).getBooksByGenre("genre1");
    }

    @Test
    void sort() {
        Book book1 = new Book("title1", "author1", "genre1", 100.0, 15);
        Book book2 = new Book("title2", "author2", "genre2", 1100.0, 125);
        Book book3 = new Book("title3", "author3", "genre3", 1200.0, 135);
        Mockito.when(bookRepository.findAll()).thenReturn(List.of(book3, book2, book1));
        List<Book> expected = List.of(book1, book2, book3);
        List<Book> sort = bookService.sort(SortType.PRICE);
        Assert.assertArrayEquals(expected.toArray(), sort.toArray());
        Mockito.verify(bookRepository).findAll();
    }

    @Test
    void getById() {
        Book expected = new Book("title1", "author1", "genre1", 100.0, 15);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(new Book("title1", "author1", "genre1", 100.0, 15)));
        Book book = bookService.get(1L);
        Assert.assertEquals(expected, book);
        Mockito.verify(bookRepository).findById(1L);
    }

    @Test
    void update() {
        Book expected = new Book("title11", "author11", "genre11", 1000.0, 150);
        expected.setId(1L);
        Book book = new Book("title3", "author2", "genre1", 1200.0, 135);
        book.setId(1L);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(expected)).thenReturn(expected);
        Book update = bookService.update(expected);

        Assert.assertEquals(expected, update);
        Mockito.verify(bookRepository).findById(1L);
        Mockito.verify(bookRepository).save(expected);
    }

    @Test
    void delete() {
        Book expected = new Book("title11", "author11", "genre11", 1000.0, 150);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(expected));
        Book delete = bookService.delete(1L);

        Assert.assertEquals(expected, delete);
        Mockito.verify(bookRepository).findById(1L);
        Mockito.verify(bookRepository).delete(expected);
    }
}