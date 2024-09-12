package com.rafa.backend.listener;

import com.rafa.backend.listener.dto.OrderCreatedEvent;
import com.rafa.backend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.rafa.backend.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {
    private final OrderService service;

    public OrderCreatedListener(OrderService service) {
        this.service = service;
    }

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEvent> message) {
        service.save(message.getPayload());
    }
}
