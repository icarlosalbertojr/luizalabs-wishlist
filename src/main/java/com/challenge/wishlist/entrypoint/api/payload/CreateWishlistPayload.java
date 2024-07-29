package com.challenge.wishlist.entrypoint.api.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class CreateWishlistPayload {

    public record Request(
            @NotEmpty
            String customerId
    ) {}

    public record Response(
            String id,
            String customerId,
            Integer maxLimit,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime createdAt
    ) {}

}
