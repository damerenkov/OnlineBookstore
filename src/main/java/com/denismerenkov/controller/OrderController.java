package com.denismerenkov.controller;

import com.denismerenkov.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService  orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    //TODO make methods: orderCreate, orderAccept, addItem, deleteItem, getUserShoppingBag, getUserCompletedOrderList
}
