package com.hendisantika.controller;

import com.hendisantika.DTO.ProductDTO;
import com.hendisantika.model.Category;
import com.hendisantika.model.Product;
import com.hendisantika.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminProductController {
    private final ProductService productService;

    @GetMapping("/admin/index")
    public String showExampleView(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/index";
    }

    @GetMapping("/admin/product")
    public String showAddProduct(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("products", productService.getAllProducts());
        return "admin/product";
    }

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




    @GetMapping("/admin/deleteProd/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        try {
            productService.deleteProductById(id);
        } catch (Exception e) {
            // Log chi tiết lỗi
            e.printStackTrace();
            return "error";  // Trả về trang lỗi nếu có sự cố
        }
        return "redirect:/admin/index";  // Điều hướng đến trang danh sách sản phẩm sau khi xóa
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";  // Trang lỗi sẽ hiển thị thông báo lỗi
    }



    @PostMapping("/admin/changeName")
    public String changeProductName(@RequestParam("id") Long id,
                                    @RequestParam("newPname") String name) {
        productService.changeProductName(id, name);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/changeDescription")
    public String changeDescription(@RequestParam("id") Long id,
                                    @RequestParam("newDescription") String description) {
        productService.changeProductDescription(id, description);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/changePrice")
    public String changePrice(@RequestParam("id") Long id,
                              @RequestParam("newPrice") Double price) {
        productService.changeProductPrice(id, price);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/changeQuantity")
    public String changeQuantity(@RequestParam("id") Long id,
                                 @RequestParam("newQuantity") int quantity) {
        productService.changeProductQuantity(id, quantity);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/changeDiscount")
    public String changeDiscount(@RequestParam("id") Long id,
                                 @RequestParam("newDiscount") int discount) {
        productService.changeProductDiscount(id, discount);
        return "redirect:/admin/index";
    }


    @PostMapping("/admin/addPictureToP")
    public String addImageToProduct(@RequestParam("file") MultipartFile file,
                                    @RequestParam("product_id") Long id) {
        productService.addImageToProduct(file, id);
        return "redirect:/admin/product";
    }
}
