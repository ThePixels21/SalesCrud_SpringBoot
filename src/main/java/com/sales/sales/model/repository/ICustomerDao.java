package com.sales.sales.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sales.sales.model.entity.Customer;

import jakarta.persistence.Tuple;

@Repository
public interface ICustomerDao extends JpaRepository<Customer, Long> {

    /**
     * Finds all the customers and their orders
     * @return all the customers and all the orders
     */
    @Query(value = "SELECT c.name AS name, c.last_name_1 AS lastname1, c.last_name_2 AS lastname2, "
            + "c.city AS city, c.category AS category, o.id AS id, o.quantity AS quantity, o.date AS date FROM "
            + "customers c LEFT JOIN orders o ON c.id = customer_id ORDER BY c.last_name_1, c.last_name_2, c.name", 
            nativeQuery = true)
    public List<Tuple> findCustomersAndTheirOrders();

    /**
     * A list of customers filtered by a keyword
     * @param keyword the filter by fullname
     * @return a list of customers filtered by a keyword
     */
    @Query("SELECT c FROM Customer c WHERE CONCAT(c.lastName1, ' ', c.lastName2, ' ', c.name) LIKE %?1% ORDER BY c.id DESC")
    public List<Customer> findByName(String keyword);

    /**
     * Get all the categories of all customers
     * @return a list of all the categories
     */
    @Query("SELECT DISTINCT c.category FROM Customer c")
    public List<String> getCategories();

    /**
     * Get all customers of a category
     * @param categoryNumber the filter category number
     * @return a list of customers filtered by the category
     */
    @Query("SELECT c FROM Customer c WHERE c.category = ?1 ORDER BY c.id DESC")
    public List<Customer> getCustomersByCategory(String categoryNumber);
}
