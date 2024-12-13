package com.hendisantika.service;

import com.hendisantika.model.CartItem;
import com.hendisantika.model.Product;
import com.hendisantika.model.ShoppingCart;
import com.hendisantika.repository.CartItemRepository;
import com.hendisantika.repository.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ShoppingCartService {

    ShoppingCartRepository shoppingCartRepository;
    ProductService productService;
    CartItemRepository cartItemRepository;

    @Getter
    private final ShoppingCart currentCart = new ShoppingCart();


    public void addToShoppingCart(Long productId, int quantity) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new EntityNotFoundException("Product not found");
        }

        CartItem existingItem = currentCart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            currentCart.getItems().add(newItem);
        }

        shoppingCartRepository.save(currentCart);
    }



    public ShoppingCart getShoppingCartBySessionToken(String sessionToken) {
        return shoppingCartRepository.findBySessionToken(sessionToken);
    }

    public CartItem updateShoppingCartItem(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with " + id + " not found"));
        cartItem.setQuantity(quantity);
        return cartItemRepository.saveAndFlush(cartItem);
    }

    public ShoppingCart removeCartIemFromShoppingCart(Long id, String sessionToken) {
        ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken);
        Set<CartItem> items = shoppingCart.getItems();
        CartItem cartItem = null;
        for (CartItem item : items) {
            if (item.getId() == id) {
                cartItem = item;
            }
        }
        items.remove(cartItem);
        cartItemRepository.delete(cartItem);
        shoppingCart.setItems(items);
        return shoppingCartRepository.save(shoppingCart);
    }

    public void clearShoppingCart(String sessionToken) {
        ShoppingCart sh = shoppingCartRepository.findBySessionToken(sessionToken);
        shoppingCartRepository.delete(sh);
    }
}
