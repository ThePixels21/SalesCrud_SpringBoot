package com.sales.sales.model.service.customer;

import java.util.List;

import com.sales.sales.model.entity.Customer;

import jakarta.persistence.Tuple;

public interface ICustomerService {

    public List<Customer> getCustomers(String keyword);

    public List<Tuple> findCustomersAndTheirOrders();

    public void saveCustomer(Customer customer);

    public Customer getCustomerById(Long id);

    public Customer updateCustomer(Customer customer);

    public void deleteCustomer(Long id);

}
