package com.sales.sales.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sales.sales.model.service.customer.ICustomerService;


@Controller
public class IndexController {
    
    private ICustomerService repo;

    /**
     * This constructor initializes the customer service interface
     * @param repo the interface to initialize
     */
    public IndexController(ICustomerService repo){
        this.repo = repo;
    }

    /**
     * This method returns the list of customers and their associated orders
     * @param model Thymeleaf model object
     * @return the index view 
     */
    @GetMapping({"", "/", "/index"})
    public String listCustomersAndOrders(Model model) {
        model.addAttribute("list", repo.findCustomersAndTheirOrders());
        return "index";
    }

}
