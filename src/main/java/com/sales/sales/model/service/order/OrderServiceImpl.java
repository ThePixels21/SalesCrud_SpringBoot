package com.sales.sales.model.service.order;

import java.util.List;

import org.springframework.data.domain.Sort;
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
    public List<Order> getOrders(Double quantity) {
        if(quantity != null) {
            return this.repo.getOrdersByQuantity(quantity);
        }
        return this.repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByDate(String date1, String date2) {
        return this.repo.getOrdersByDate(date1, date2);
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
