package com.denismerenkov.service;

import com.denismerenkov.model.Order;

import java.util.List;

public interface OrderService {

    Order addNew(long userId);
    Order acceptShoppingBag(long orderId);

    Order get(long orderId);

    Order getShoppingCard(long userId);

    List<Order> getCompletedUserOrders(long userId);

    Order addItem(long orderId,long bookId);

    Order deleteItem(long orderId,long bookId);
}
