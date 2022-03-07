package com.example.carrental.controller;

import com.example.carrental.entity.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("customer", Customer.builder().id(1L).username("LinMingWei").build());
        return "index";
    }

}
