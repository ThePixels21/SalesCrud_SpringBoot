package com.sales.sales.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sales.sales.model.entity.Seller;
import com.sales.sales.model.service.seller.ISellerService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("sellers")
public class SellerController {

    private ISellerService sellerService;

    public SellerController(ISellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping({ "", "/" })
    public String listSellers(Model model, @Param("keyword") String keyword) {
        model.addAttribute("keyword", keyword);
        if (keyword != null && validateNumbers(keyword)) {
            Seller seller = null;
            try {
                seller = sellerService.getSellerById((Long.parseLong(keyword)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "redirect:/sellers";
            }
            if (seller != null) {
                model.addAttribute("seller", seller);
                return "sellers/update";
            }
        }
        model.addAttribute("sellers", sellerService.getSellers(keyword));
        return "sellers/sellers";
    }

    @GetMapping("/register")
    public String registerSeller(Model model) {
        model.addAttribute("seller", new Seller());
        return "sellers/register";
    }

    @PostMapping("/register")
    public String saveSeller(@Valid Seller seller, BindingResult result) {
        if (result.hasErrors()) {
            return "sellers/register";
        }
        sellerService.saveSeller(seller);
        return "redirect:/sellers";
    }

    @GetMapping("/update/{id}")
    public String formUpdateSeller(@PathVariable Long id, Model model) {
        model.addAttribute("seller", sellerService.getSellerById(id));
        return "sellers/update";
    }

    @PostMapping("/update/{id}")
    public String updateSeller(@PathVariable Long id, @Valid Seller seller, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("seller", sellerService.getSellerById(id));
            return "sellers/update";
        }
        Seller s = sellerService.getSellerById(id);
        s.setName(seller.getName());
        s.setLastName1(seller.getLastName1());
        s.setLastName2(seller.getLastName2());
        s.setCity(seller.getCity());
        s.setCommission(seller.getCommission());

        sellerService.updateSeller(s);

        return "redirect:/sellers";
    }

    @GetMapping("/delete/{id}")
    public String deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return "redirect:/sellers";
    }

    private boolean validateNumbers(String data) {
        return data.matches("[0-9]*");
    }

}
