package com.ecom.order_mgt.controller;

import com.ecom.order_mgt.exception.OrderNotAvailable;
import com.ecom.order_mgt.model.dao.Orders;
import com.ecom.order_mgt.repo.OrderRepository;
import com.ecom.order_mgt.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderService orderService;

    @Spy
    private OrderRepository orderRepository;

    @Test
    public void getOrders() {
        Long orderId = 1L;
        Long itemId = 1L;
        Orders orders = new Orders(1L, 1L, itemId, "Ordering Apple", LocalDateTime.now());
        doReturn(Arrays.asList(orders)).when(orderRepository).findAll();
        List<Orders> allOrders = orderService.getAllOrders();
        assertEquals(1, allOrders.size());
        assertEquals(orderId, allOrders.get(0).getId());
        assertEquals(itemId, allOrders.get(0).getItemId());
    }

    @Test
    public void getOrderById() {
        Long orderId = 1L;
        Long itemId = 1L;
        Orders orders = new Orders(1L, 1L, itemId, "Ordering Apple", LocalDateTime.now());
        doReturn(Optional.of(orders)).when(orderRepository).findById(orderId);
        Orders orderById = orderService.getOrderById(orderId);
        assertEquals(itemId, orderById.getItemId());
        assertEquals(orderId, orderById.getId());
    }

    @Test
    public void shouldReturnNotFoundExceptionOrderById() {
        Long orderId = 1L;
        doReturn(Optional.empty()).when(orderRepository).findById(orderId);
        assertThrows(OrderNotAvailable.class, () -> orderService.getOrderById(orderId));
    }
}