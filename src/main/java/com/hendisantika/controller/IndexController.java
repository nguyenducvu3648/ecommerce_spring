package com.hendisantika.controller;

import com.hendisantika.model.Product;
import com.hendisantika.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {
    private final ProductService productService;

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }
    @GetMapping(value = "/search")
    @ResponseBody
    public ModelAndView search(@RequestParam("value") String value) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("fragments/searchFragment");
        List<Product> products = productService.searchProductByNameLike(value);
        mv.addObject("products", products);
        return mv;
    }
}
