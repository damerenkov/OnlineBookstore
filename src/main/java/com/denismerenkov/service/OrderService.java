package com.denismerenkov.service;

import com.denismerenkov.model.Book;
import com.denismerenkov.model.Order;

import java.util.List;

public interface OrderService {

    void addNew(long userId);
    void acceptShoppingBag(long orderId);

    Order get(long orderId);

    Order getShoppingCard(long userId);

    List<Order> getCompletedUserUOrders(long userId);

    Order addItem(long orderId,long bookId);

    Order deleteItem(long orderId,long bookId);
}
