package com.hendisantika.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/30/22
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "wishlist")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String sessionToken;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<WishListItem> items = new HashSet<WishListItem>();

}
