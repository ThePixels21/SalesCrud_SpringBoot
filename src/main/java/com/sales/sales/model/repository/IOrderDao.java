package com.sales.sales.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sales.sales.model.entity.Order;

@Repository
public interface IOrderDao extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.quantity = ?1 ORDER BY o.id DESC")
    public List<Order> getOrdersByQuantity(Double quantity);

    @Query(value = "SELECT * FROM orders WHERE date >= ?1 AND date <= ?2 ORDER BY date", nativeQuery = true)
    public List<Order> getOrdersByDate(String date1, String date2);

}
