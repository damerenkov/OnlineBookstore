package com.denismerenkov.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books", uniqueConstraints =
@UniqueConstraint(columnNames = {"title", "author", "genre"}))
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    private String genre;
    @NonNull
    private double price;
    @NonNull
    private int quantityInStock;

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    public void incrementQuantity() {
        this.quantityInStock += 1;
    }

    public void decrementQuantity() {
        if(this.quantityInStock < 1){
            throw new IndexOutOfBoundsException("Quantity in stock equals 0");
        }
        this.quantityInStock -= 1;
    }
}
