package com.sales.sales.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sales.sales.model.service.customer.ICustomerService;


@Controller
public class IndexController {
    
    private ICustomerService repo;

    public IndexController(ICustomerService repo){
        this.repo = repo;
    }

    @GetMapping({"", "/", "/index"})
    public String listCustomersAndOrders(Model model) {
        model.addAttribute("list", repo.findCustomersAndTheirOrders());
        return "index";
    }

}
