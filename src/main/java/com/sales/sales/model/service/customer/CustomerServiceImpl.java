package com.sales.sales.model.service.customer;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sales.sales.model.entity.Customer;
import com.sales.sales.model.repository.ICustomerDao;

import jakarta.persistence.Tuple;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private ICustomerDao repo;

    public CustomerServiceImpl(ICustomerDao repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getCustomers(String keyword) {
        if(keyword != null) {
            return this.repo.findByName(keyword);
        }
        return this.repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<Tuple> findCustomersAndTheirOrders() {
        return this.repo.findCustomersAndTheirOrders();
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer) {
        this.repo.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return this.repo.findById(id).orElse(null);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return this.repo.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        this.repo.deleteById(id);
    }
    
}
