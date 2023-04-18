package com.sales.sales.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sales.sales.model.entity.Customer;
import com.sales.sales.model.service.customer.ICustomerService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("customers")
public class CustomerController {

    private ICustomerService customerService;

    /**
     * This constructor initializes the customer service interface
     * @param customerService customer service interface
     */
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * This method list the customers by filters
     * @param model Thymeleaf model object
     * @param keyword filter by name, surname or id
     * @param categoryNumber filter by category
     * @return the customers principal view
     */
    @GetMapping({ "", "/" })
    public String listCustomers(Model model, @Param("keyword") String keyword,
            @Param("categoryNumber") String categoryNumber) {
        model.addAttribute("categories", customerService.getCategories());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryNumber", categoryNumber);
        if (keyword != null && validateNumbers(keyword)) {
            Customer customer = null;
            try {
                customer = customerService.getCustomerById((Long.parseLong(keyword)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "redirect:/customers";
            }
            if (customer != null) {
                model.addAttribute("customer", customer);
                return "customers/update";
            }
        } else if (categoryNumber != null && !categoryNumber.isEmpty()) {
            model.addAttribute("customers", customerService.getCustomersByCategory(categoryNumber));
        } else {
            model.addAttribute("customers", customerService.getCustomers(keyword));
        }
        return "customers/customers";
    }

    /**
     * This method is used to call the register view
     * @param model Thymeleaf model object
     * @return the customer registration view
     */
    @GetMapping("/register")
    public String registerCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/register";
    }

    /**
     * This method registers the customer in the database
     * @param customer The customer to be registered
     * @param result errors result
     * @return if has errors, it returns the register view, else it returns the customers principal view
     */
    @PostMapping("/register")
    public String saveCustomer(@Valid Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return "customers/register";
        }
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }

    /**
     * This method load the form to update a customer
     * @param id the id of the customer
     * @param model Thymeleaf model object
     * @return the update form view
     */
    @GetMapping("/update/{id}")
    public String formUpdateCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customers/update";
    }

    /**
     * This method updates the customer
     * @param id the id of the customer
     * @param customer the customerto update
     * @param result the errors result
     * @param model Thyemelaf model object
     * @return if has errors, returns the updte view again, if not, returns the customers principal view
     */
    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customer", customerService.getCustomerById(id));
            return "customers/update";
        }
        Customer c = customerService.getCustomerById(id);
        c.setName(customer.getName());
        c.setLastName1(customer.getLastName1());
        c.setLastName2(customer.getLastName2());
        c.setCity(customer.getCity());
        c.setCategory(customer.getCategory());

        customerService.updateCustomer(c);

        return "redirect:/customers";
    }

    /**
     * This method deletes a customer
     * @param id the id of the customer
     * @return the customers principal view
     */
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

    /**
     * This method validates that the String parameter is a number
     * @param data the parameter to validate
     * @return returns true if the parameter is a number, false otherwise
     */
    private boolean validateNumbers(String data) {
        return data.matches("[0-9]*");
    }

}
