package com.denismerenkov.controller;

import com.denismerenkov.dto.ResponseResult;
import com.denismerenkov.model.Order;
import com.denismerenkov.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/create/{userId}")
    public ResponseEntity<ResponseResult<Order>> orderCreate(@PathVariable long userId) {
        try {
            Order order = this.orderService.addNew(userId);
            return new ResponseEntity<>(new ResponseResult<>(null, order), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accept/{orderId}")
    public ResponseEntity<ResponseResult<Order>> orderAccept(@PathVariable long orderId) {
        try {
            Order order = this.orderService.acceptShoppingBag(orderId);
            return new ResponseEntity<>(new ResponseResult<>(null, order), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/addItem/{orderId}")
    public ResponseEntity<ResponseResult<Order>> addItem(@PathVariable long orderId, @RequestParam long bookId) {
        try {
            Order order = this.orderService.addItem(orderId, bookId);
            return new ResponseEntity<>(new ResponseResult<>(null, order), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/deleteItem/{orderId}")
    public ResponseEntity<ResponseResult<Order>> deleteItem(@PathVariable long orderId, @RequestParam long bookId) {
        try {
            Order order = this.orderService.deleteItem(orderId, bookId);
            return new ResponseEntity<>(new ResponseResult<>(null, order), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseResult<Order>> getUserShoppingBag(@PathVariable long userId) {
        try {
            Order order = this.orderService.getShoppingCard(userId);
            return new ResponseEntity<>(new ResponseResult<>(null, order), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<ResponseResult<List<Order>>> getUserCompletedOrderList(@PathVariable long userId) {
        try {
            List<Order> completedUserOrders = this.orderService.getCompletedUserOrders(userId);
            return new ResponseEntity<>(new ResponseResult<>(null, completedUserOrders), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
