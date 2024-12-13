package com.hendisantika.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Transient;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "shopping_cart")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Transient
    private Double totalPrice;
    @Transient
    private int itemsNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CartItem> items = new HashSet<CartItem>();
    private String sessionToken;
}
