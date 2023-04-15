package com.sales.sales.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sales.sales.model.entity.Customer;

@Repository
public interface ICustomerDao extends JpaRepository<Customer, Long>{
    @Query("SELECT c, o FROM Customer c LEFT JOIN FETCH c.orders o ORDER BY c.lastName1, c.lastName2, c.name")
    public Customer findCustomersAndTheirOrders();
}
