package com.hendisantika.controller;

import com.hendisantika.model.ShoppingCart;
import com.hendisantika.service.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/addToCart")
    public String addToCart(
            @RequestParam("id") Long id,
            @RequestParam("quantity") int quantity,
            Model model) {
        try {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }

            shoppingCartService.addToShoppingCart(id, quantity);
            ShoppingCart updatedCart = shoppingCartService.getCurrentCart();

            model.addAttribute("cart", updatedCart);
            model.addAttribute("successMessage", "Product added to cart successfully!");

            return "shoppingCart";

        } catch (IllegalArgumentException | EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());

            return "shoppingCart";
        }
    }



    @GetMapping("/shoppingCart")
    public String showShoppingCartView(Model model) {
        ShoppingCart cart = shoppingCartService.getCurrentCart();
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartTotal", cart.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum());
        return "shoppingCart";
    }

    @PostMapping("/updateShoppingCart")
    public String updateCartItem(@RequestParam("item_id") Long id,
                                 @RequestParam("quantity") int quantity) {

        shoppingCartService.updateShoppingCartItem(id, quantity);
        return "redirect:shoppingCart";
    }

    @GetMapping("/removeCartItem/{id}")
    public String removeItem(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        log.info("got here ...");
        shoppingCartService.removeCartIemFromShoppingCart(id, sessionToken);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/clearShoppingCart")
    public String clearShoppingString(HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        request.getSession(false).removeAttribute("sessionToken");
        shoppingCartService.clearShoppingCart(sessionToken);
        return "redirect:/shoppingCart";
    }
}
