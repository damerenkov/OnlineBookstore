package com.denismerenkov.service.impl;

import com.denismerenkov.model.Book;
import com.denismerenkov.model.Order;
import com.denismerenkov.model.user.User;
import com.denismerenkov.repository.OrderRepository;
import com.denismerenkov.service.BookService;
import com.denismerenkov.service.OrderService;
import com.denismerenkov.service.UserService;
import com.denismerenkov.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private UserService userService;
    private BookService bookService;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @Transactional
    public Order addNew(long userId) {
        Order order = new Order();
        User user = this.userService.findById(userId);
        order.setUser(user);
        Optional<Order> any = user.getOrders().stream().filter(o -> o.getStatus().equals(OrderStatus.STATUS_SHOPPING_CARD)).findAny();

        if (!any.isPresent()) {
            return this.orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("User already has the shopping bag!");
        }

    }

    @Override
    public Order acceptShoppingBag(long orderId) {
        Order order = this.get(orderId);
        if (order.getStatus() == OrderStatus.STATUS_SHOPPING_CARD || order.getBooks().isEmpty()) {
            order.setStatus(OrderStatus.STATUS_ORDER);
            return this.orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Shopping bag is empty!");
        }
    }

    @Override
    public Order get(long orderId) {
        return this.orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("No such order!")
        );
    }

    @Override
    @Transactional
    public Order getShoppingCard(long userId) {
        this.userService.findById(userId);
        List<Order> ordersByUserIdAndStatus = this.orderRepository.getOrdersByUserIdAndStatus(userId, OrderStatus.STATUS_SHOPPING_CARD);
        if (!ordersByUserIdAndStatus.isEmpty()) {
            return ordersByUserIdAndStatus.get(0);
        } else {
            throw new IllegalArgumentException("User does not have any shopping bag");
        }
    }

    @Override
    @Transactional
    public List<Order> getCompletedUserOrders(long userId) {
        this.userService.findById(userId);
        return this.orderRepository.getOrdersByUserIdAndStatus(userId, OrderStatus.STATUS_ORDER);
    }

    @Override
    public Order addItem(long orderId, long bookId) {
        Book book = this.bookService.get(bookId);
        Order order = this.get(orderId);
        if (order.getStatus() == OrderStatus.STATUS_SHOPPING_CARD){

            book.decrementQuantity();
            this.bookService.update(book);
            order.setBooks(order.addBook(book));
            return this.orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("This order is closed for changes!");
        }

    }

    @Override
    public Order deleteItem(long orderId, long bookId) {
        Book book = this.bookService.get(bookId);
        Order order = this.get(orderId);
        if (order.getStatus() == OrderStatus.STATUS_SHOPPING_CARD){

            if (order.getBooks().isEmpty()) {
                throw new IllegalArgumentException("Order is empty");
            }
            order.getBooks().stream().filter(Predicate.isEqual(book)).findFirst().orElseThrow(
                    () -> new IllegalArgumentException("This book is not on order!")
            );
            book.incrementQuantity();
            this.bookService.update(book);

            order.setBooks(order.deleteBook(book));
            return this.orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("This order is closed for changes!");
        }
    }


}
