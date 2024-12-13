package com.hendisantika.service;

import com.hendisantika.DTO.ProductDTO;
import com.hendisantika.model.Product;
import com.hendisantika.repository.CategoryRepository;
import com.hendisantika.repository.CouponRepository;
import com.hendisantika.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CouponRepository couponRepository;



    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public void deleteProductById(Long id) {
        System.out.println("Deleting product with id: " + id);
        productRepository.deleteById(id);
    }



    public Product addProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setImage(productDTO.getImageUrl());
        return productRepository.save(product);
    }
    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found"));


        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setImage(updatedProduct.getImage());

        return productRepository.save(existingProduct);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found"));
    }

    public List<Product> searchProductByNameLike(String value) {
        return productRepository.findByNameContainingIgnoreCase(value);
    }

    public List<String> getAllBrands() {
        return productRepository.findAllBrandsDistincts();
    }

}
