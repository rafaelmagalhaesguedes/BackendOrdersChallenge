package com.rafa.backend.repository;

import com.rafa.backend.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Long> {
    Page<Order> findAllByCustomerId(Long customerId, PageRequest pageRequest);
}
