package com.sales.sales.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sales.sales.model.entity.Customer;

import jakarta.persistence.Tuple;

@Repository
public interface ICustomerDao extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT c.name AS cname, c.last_Name_1 AS clastname1, c.last_Name_2 AS clastname2, "
            + "c.city AS city, c.category AS category, o.id AS id, o.quantity AS quantity, o.date AS date FROM "
            + "customers c LEFT JOIN orders o ON c.id = customer_id ORDER BY c.last_Name_1, c.last_Name_2, c.name", 
            nativeQuery = true)
    public List<Tuple> findCustomersAndTheirOrders();
}
