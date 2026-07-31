package com.supermarketsystem.supermarket_system_g8_1_03.controller;

import com.supermarketsystem.supermarket_system_g8_1_03.service.StorefrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private StorefrontService storefrontService;

    @GetMapping("/")
    public String storeFront(Model model) {
        // Feed the navbar dropdown dynamically from MS SQL
        model.addAttribute("categories", storefrontService.getRootCategories());

        // Feed the storefront section feeds
        model.addAttribute("promotions", storefrontService.getPromotedProducts());
        model.addAttribute("newArrivals", storefrontService.getNewestArrivals());
        model.addAttribute("allProducts", storefrontService.getAllProducts());

        return "index";
    }
}