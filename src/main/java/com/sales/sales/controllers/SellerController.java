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

    /**
     * This constructor initializes the seller service interface
     * @param sellerService the seller service interface
     */
    public SellerController(ISellerService sellerService) {
        this.sellerService = sellerService;
    }

    /**
     * This method list the sellers by filters
     * @param model Thymeleaf model object
     * @param keyword the keyword to filter by name, surnames or id
     * @return returns the sellers principal view
     */
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

    /**
     * This method loads the register seller view
     * @param model Thymeleaf model object
     * @return returns the register seller form
     */
    @GetMapping("/register")
    public String registerSeller(Model model) {
        model.addAttribute("seller", new Seller());
        return "sellers/register";
    }

    /**
     * This method saves a Seller to the database
     * @param seller the seller to save
     * @param result Thymeleaf model object
     * @return if successful returns the sellers principal view, otherwise returns the register form again
     */
    @PostMapping("/register")
    public String saveSeller(@Valid Seller seller, BindingResult result) {
        if (result.hasErrors()) {
            return "sellers/register";
        }
        sellerService.saveSeller(seller);
        return "redirect:/sellers";
    }

    /**
     * This method loads the update seller view
     * @param id the id of the seller to update
     * @param model Thymeleaf model object
     * @return returns the update seller form
     */
    @GetMapping("/update/{id}")
    public String formUpdateSeller(@PathVariable Long id, Model model) {
        model.addAttribute("seller", sellerService.getSellerById(id));
        return "sellers/update";
    }

    /**
     * This method updates a seller
     * @param id the id of the seller to update
     * @param seller The seller updated
     * @param result errors form result
     * @param model Thymeleaf model object
     * @return if successful, returns the sellers principal view, otherwise returns the update form again
     */
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

    /**
     * This method deletes a Seller
     * @param id the id of the seller to delete
     * @return the sellers principal view
     */
    @GetMapping("/delete/{id}")
    public String deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return "redirect:/sellers";
    }

    /**
     * This method validates if the String parameter is a number
     * @param data the String to validate
     * @return returns true if the String parameter is a number, otherwise false
     */
    private boolean validateNumbers(String data) {
        return data.matches("[0-9]*");
    }

}
