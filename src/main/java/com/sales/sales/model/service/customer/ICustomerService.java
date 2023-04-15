package com.sales.sales.model.service.customer;

import java.util.List;

import com.sales.sales.model.entity.Customer;

public interface ICustomerService {

    public List<Customer> getCustomers();

    public List<Customer> findCustomersAndTheirOrders();

    public void saveCustomer(Customer customer);

    public Customer getCustomerById(Long id);

    public Customer updateCustomer(Customer customer);

    public void deleteCustomer(Long id);

}
