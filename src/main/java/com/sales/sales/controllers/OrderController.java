package com.sales.sales.controllers;

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

    public OrderController(IOrderService orderService, ICustomerService customerService, ISellerService sellerService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.sellerService = sellerService;
    }

    @GetMapping({ "", "/" })
    public String listOrders(Model model, @Param("quantity") Double quantity, @Param("id") Long id) {
        model.addAttribute("quantity", quantity);
        if (id != null) {
            Order order = null;
            try {
                order = orderService.getOrderById(id);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "redirect:/orders";
            }
            if (order != null) {
                model.addAttribute("order", order);
                return "orders/update";
            }
        }
        model.addAttribute("orders", orderService.getOrders(quantity));
        return "orders/orders";
    }

    @GetMapping("/register")
    public String registerOrder(Model model) {
        model.addAttribute("order", new Order());
        return "orders/register";
    }

    @PostMapping("/register")
    public String saveOrder(@Valid Order order, BindingResult result) {
        if (result.hasErrors()) {
            return "orders/register";
        }
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/update/{id}")
    public String formUpdateOrder(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("customers", customerService.getCustomers(null));
        model.addAttribute("sellers", sellerService.getSellers(null));
        return "orders/update";
    }

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

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

}
