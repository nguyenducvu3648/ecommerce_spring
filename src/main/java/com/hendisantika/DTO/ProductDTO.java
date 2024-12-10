package com.hendisantika.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String brand;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;
//    private Long discountId;
}
