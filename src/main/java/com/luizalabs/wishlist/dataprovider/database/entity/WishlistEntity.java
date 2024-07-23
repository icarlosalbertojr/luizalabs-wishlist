package com.luizalabs.wishlist.dataprovider.database.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document("wishlists")
@Getter
@Setter
public class WishlistEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String customerId;
    private Set<String> products;
    private Integer maxLimit;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

}
