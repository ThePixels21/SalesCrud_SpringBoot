package com.sales.sales.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sales.sales.model.entity.Customer;

import jakarta.persistence.Tuple;

@Repository
public interface ICustomerDao extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT c.name AS name, c.last_name_1 AS lastname1, c.last_name_2 AS lastname2, "
            + "c.city AS city, c.category AS category, o.id AS id, o.quantity AS quantity, o.date AS date FROM "
            + "customers c LEFT JOIN orders o ON c.id = customer_id ORDER BY c.last_name_1, c.last_name_2, c.name", 
            nativeQuery = true)
    public List<Tuple> findCustomersAndTheirOrders();

    @Query("SELECT c FROM Customer c WHERE CONCAT(c.lastName1, ' ', c.lastName2, ' ', c.name) LIKE %?1% ORDER BY c.id DESC")
    public List<Customer> findByName(String keyword);

    @Query("SELECT DISTINCT c.category FROM Customer c")
    public List<String> getCategories();

    @Query("SELECT c FROM Customer c WHERE c.category = ?1 ORDER BY c.id DESC")
    public List<Customer> getCustomersByCategory(String categoryNumber);
}
