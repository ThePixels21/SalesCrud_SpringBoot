package com.sales.sales.model.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sales.sales.model.entity.Order;
import com.sales.sales.model.repository.IOrderDao;

@Service
public class OrderServiceImpl implements IOrderService {

    private IOrderDao repo;

    public OrderServiceImpl(IOrderDao repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrders() {
        return this.repo.findAll();
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        this.repo.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return this.repo.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Order order) {
        return this.repo.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        this.repo.deleteById(id);
    }
    
}
