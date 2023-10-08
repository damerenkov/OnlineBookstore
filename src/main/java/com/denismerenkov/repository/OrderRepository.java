package com.denismerenkov.repository;

import com.denismerenkov.model.Order;
import com.denismerenkov.util.OrderStatus;
import org.aspectj.weaver.Lint;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByUserIdAndStatus(long userId, OrderStatus status);

}
