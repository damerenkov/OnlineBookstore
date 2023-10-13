package com.denismerenkov.model;


import com.denismerenkov.model.user.User;
import com.denismerenkov.util.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private OrderStatus status = OrderStatus.STATUS_SHOPPING_CARD;

    private double totalPrice = 0;

    @ManyToMany
    @JoinTable(
            name = "order_books",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    List<Book> books = new ArrayList<>();

    public List<Book> addBook(Book book){
        this.books.add(book);
        this.totalPrice += book.getPrice();
        return this.books;
    }
    public List<Book> deleteBook(Book book){
        this.books.remove(book);
        this.totalPrice -= book.getPrice();
        return this.books;
    }
}
