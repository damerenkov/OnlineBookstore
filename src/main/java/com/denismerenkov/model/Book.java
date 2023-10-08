package com.denismerenkov.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books", uniqueConstraints =
@UniqueConstraint(columnNames = {"title", "author", "genre"}))
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String author;
    private String genre;
    private double price;
    private int quantityInStock = 0;

    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    public void incrementQuantity() {
        this.quantityInStock += 1;
    }

    public void decrementQuantity() {
        this.quantityInStock -= 1;
    }
}
