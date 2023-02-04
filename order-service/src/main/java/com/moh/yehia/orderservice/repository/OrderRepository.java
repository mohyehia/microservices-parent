package com.moh.yehia.orderservice.repository;

import com.moh.yehia.orderservice.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
