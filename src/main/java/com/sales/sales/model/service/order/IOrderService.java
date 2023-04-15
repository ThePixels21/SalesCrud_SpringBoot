package com.sales.sales.model.service.order;

import java.util.List;

import com.sales.sales.model.entity.Order;

public interface IOrderService {
    
    public List<Order> getOrders();

    public void saveOrder(Order order);

    public Order getOrderById(Long id);

    public Order updateOrder(Order order);

    public void deleteOrder(Long id);

}
