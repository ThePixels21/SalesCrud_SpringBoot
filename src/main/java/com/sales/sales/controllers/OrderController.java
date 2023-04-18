package com.sales.sales.controllers;

import java.util.ArrayList;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sales.sales.model.entity.Order;
import com.sales.sales.model.service.customer.ICustomerService;
import com.sales.sales.model.service.order.IOrderService;
import com.sales.sales.model.service.seller.ISellerService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("orders")
public class OrderController {

    private IOrderService orderService;
    private ICustomerService customerService;
    private ISellerService sellerService;

    /**
     * This constructor initializes all the services
     * @param orderService order service interface
     * @param customerService customer service interface
     * @param sellerService seller service interface
     */
    public OrderController(IOrderService orderService, ICustomerService customerService, ISellerService sellerService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.sellerService = sellerService;
    }

    /**
     * This method list the orders by filters
     * @param model Thymeleaf model object
     * @param quantity Quantity filter
     * @param id Id filter
     * @return the orders principal view
     */
    @GetMapping({ "", "/" })
    public String listOrders(Model model, @Param("quantity") Double quantity, @Param("id") Long id) {
        model.addAttribute("quantity", quantity);
        model.addAttribute("id", id);
        if (id != null) {
            Order order = orderService.getOrderById(id);
            if (order != null) {
                model.addAttribute("order", order);
                return "orders/update";
            } else {
                model.addAttribute("orders", new ArrayList<Order>());
            }
        }else {
            model.addAttribute("orders", orderService.getOrders(quantity));
        }
        model.addAttribute("date1", new String());
        model.addAttribute("date2", new String());
        return "orders/orders";
    }

    /**
     * This method filters the orders by date
     * @param date1 first date
     * @param date2 second date
     * @param model Thymeleaf model object
     * @return the orders principal view filtered
     */
    @PostMapping("/datefilter")
    public String getDateFilter(String date1, String date2, Model model) {
        model.addAttribute("orders", orderService.getOrdersByDate(date1, date2));
        model.addAttribute("activatebtn", true);
        return "orders/orders";
    }

    /**
     * get the register order view
     * @param model Thyemelaf model object
     * @return  the register view
     */
    @GetMapping("/register")
    public String registerOrder(Model model) {
        model.addAttribute("order", new Order());
        return "orders/register";
    }

    /**
     * this method saves an order to the database
     * @param order the order to be saved
     * @param result errors result object
     * @return if successful returns the order principal view, otherwise returns the register view again
     */
    @PostMapping("/register")
    public String saveOrder(@Valid Order order, BindingResult result) {
        if (result.hasErrors()) {
            return "orders/register";
        }
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    /**
     * This method load the update order view
     * @param id the id of the order to edit
     * @param model Thymeleaf model object
     * @return the update order view
     */
    @GetMapping("/update/{id}")
    public String formUpdateOrder(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("customers", customerService.getCustomers(null));
        model.addAttribute("sellers", sellerService.getSellers(null));
        return "orders/update";
    }

    /**
     * This method updates an order
     * @param id the id of the order
     * @param order the order to update
     * @param result errors form result
     * @param model Thymeleaf model object
     * @return if successful returns the order principal view, otherwise returns the update form again
     */
    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id, @Valid Order order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.getCustomers(null));
            model.addAttribute("sellers", sellerService.getSellers(null));
            model.addAttribute("order", orderService.getOrderById(id));
            System.out.println("hay un error");
            return "orders/update";
        }
        Order o = orderService.getOrderById(id);
        o.setQuantity(order.getQuantity());
        o.setDate(order.getDate());
        o.setCustomer(order.getCustomer());
        o.setSeller(order.getSeller());

        orderService.updateOrder(o);

        return "redirect:/orders";
    }

    /**
     * This method deletes an order from the database
     * @param id the id of the order to be deleted
     * @return return the orders principal view
     */
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

}
