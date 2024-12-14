package com.hendisantika.controller;

import com.hendisantika.DTO.ProductDTO;
import com.hendisantika.model.Category;
import com.hendisantika.model.Product;
import com.hendisantika.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Admin")
public class AdminProductController {
    private final ProductService productService;

    @Operation(summary = "Admin page")
    @GetMapping("/admin/index")
    public String showExampleView(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/index";
    }

    @Operation(summary = "All products")
    @GetMapping("/admin/product")
    public String showAddProduct(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("products", productService.getAllProducts());
        return "admin/product";
    }

    @Operation(summary = "Add new product")
    @PostMapping("/admin/addP")
    public ResponseEntity<?> addProduct(@RequestParam("name") String name,
                                        @RequestParam("brand") String brand,
                                        @RequestParam("desc") String description,
                                        @RequestParam("price") Double price,
                                        @RequestParam("quantity") Integer quantity,
                                        @RequestParam("imageUrl") String imageUrl) {
        try {
            ProductDTO productDTO = new ProductDTO(name, brand, description, price, quantity, imageUrl);
            Product product = productService.addProduct(productDTO);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Get product to edit")
    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Product productDTO = productService.getProductById(id);
            model.addAttribute("product", productDTO);
            return "admin/edit-product";
        } catch (NoSuchElementException e) {
            model.addAttribute("error", "Product not found.");
            return "error";
        }
    }

    @Operation(summary = "Edit product")
    @PostMapping("/admin/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("product") Product product, Model model) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            model.addAttribute("product", updatedProduct);
            return "redirect:/admin/index";
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while updating product.");
            return "error";
        }
    }

    @Operation(summary = "Get product to delete")
    @GetMapping("/admin/deleteProd/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        try {
            productService.deleteProductById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "redirect:/admin/index";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

}
