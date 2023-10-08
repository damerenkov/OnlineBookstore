package com.denismerenkov.service;

import com.denismerenkov.model.Book;
import com.denismerenkov.model.Order;
import com.denismerenkov.repository.OrderRepository;
import com.denismerenkov.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public void addNew(long userId) {
        Order order = new Order();
        order.setUser(this.userService.findById(userId));
        try {
            this.orderRepository.save(order);
        } catch (Exception e) {
            throw new IllegalArgumentException("User already has an order!");
        }
    }

    @Override
    public void acceptShoppingBag(long orderId) {
        Order order = this.get(orderId);
        if(order.getStatus() == OrderStatus.STATUS_SHOPPING_CARD){
            order.setStatus(OrderStatus.STATUS_ORDER);
            this.orderRepository.save(order);
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
        return this.orderRepository.getOrdersByUserIdAndStatus(userId, OrderStatus.STATUS_SHOPPING_CARD).get(0);
    }

    @Override
    @Transactional
    public List<Order> getCompletedUserUOrders(long userId) {
        this.userService.findById(userId);
        return this.orderRepository.getOrdersByUserIdAndStatus(userId, OrderStatus.STATUS_ORDER);
    }

    @Override
    public Order addItem(long orderId, long bookId) {
        Book book = this.bookService.get(bookId);
        Order order = this.get(orderId);

        book.incrementQuantity();
        this.bookService.update(book);

        order.setBooks(order.addBook(book));
        return this.orderRepository.save(order);
    }

    @Override
    public Order deleteItem(long orderId, long bookId) {
        Book book = this.bookService.get(bookId);
        Order order = this.get(orderId);

        if (order.getBooks().isEmpty()) {
            throw new IllegalArgumentException("Order is empty");
        }
        order.getBooks().stream().filter(Predicate.isEqual(book)).findFirst().orElseThrow(
                () -> new IllegalArgumentException("This book is not on order!")
        );
        book.decrementQuantity();
        this.bookService.update(book);

        order.setBooks(order.deleteBook(book));
        return this.orderRepository.save(order);
    }


}
