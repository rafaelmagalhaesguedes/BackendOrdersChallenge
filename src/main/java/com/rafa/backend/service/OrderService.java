package com.rafa.backend.service;

import com.rafa.backend.controller.dto.OrderResponse;
import com.rafa.backend.entity.Order;
import com.rafa.backend.entity.OrderItem;
import com.rafa.backend.listener.dto.OrderCreatedEvent;
import com.rafa.backend.repository.OrderRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(OrderCreatedEvent event) {
        var entity = new Order();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());
        entity.setItems(getOrderItems(event));
        entity.setTotal(getTotal(event));

        orderRepository.save(entity);
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
        var orders = orderRepository.findAllByCustomerId(customerId, pageRequest);
        return orders.map(OrderResponse::fromEntity);
    }

    public BigDecimal findTotalOrdersByCustomerId(Long customerId) {
        var aggregations = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum("total").as("total")
        );

        AggregationResults<Document> response = mongoTemplate.aggregate(aggregations, "order", Document.class);
        Document result = response.getUniqueMappedResult();

        if (result == null || result.get("total") == null) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(result.get("total").toString());
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens()
                .stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.itens()
                .stream()
                .map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco()))
                .toList();
    }
}
