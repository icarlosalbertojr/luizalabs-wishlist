package com.challenge.wishlist.entrypoint.impl;

import com.challenge.wishlist.core.domain.Wishlist;
import com.challenge.wishlist.core.repository.WishlistRepository;
import com.challenge.wishlist.dataprovider.database.repository.MongoWishlistRepository;
import com.challenge.wishlist.entrypoint.api.payload.AddWishlistProductPayload;
import com.challenge.wishlist.entrypoint.api.payload.CreateWishlistPayload;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WishlistControllerImplTest {

    @LocalServerPort
    private int port;
    private static final String BASE_URL = "/wishlist";
    private static final String CUSTOMER_ID = "customer1";
    private static final String PRODUCT_ID = "product1";

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private MongoWishlistRepository mongoWishlistRepository;

    @Value("${service.params.wishlist.maxLimit}")
    private Integer maxLimit;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterEach
    public void setDown() {
        mongoWishlistRepository.deleteAll();
    }

    @Test
    public void shouldCreateWishlistSuccessfully() {
        CreateWishlistPayload.Request requestPayload = new CreateWishlistPayload.Request(CUSTOMER_ID);
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(CREATED.value())
                .body("customerId", equalTo(CUSTOMER_ID))
                .body("id", notNullValue())
                .body("maxLimit", equalTo(20)) // Assuming default maxLimit is 5
                .body("createdAt", notNullValue());
    }

    @Test
    public void shouldNotCreateIfCustomerAlreadyHasWishlist() {
        CreateWishlistPayload.Request requestPayload = new CreateWishlistPayload.Request(CUSTOMER_ID);
        Wishlist wishlist = new Wishlist(CUSTOMER_ID, 20);
        wishlistRepository.save(wishlist);
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(CONFLICT.value())
                .body("detail", equalTo("customer already has wishlist"));
    }

    @Test
    public void shouldNotCreateWishlistIfRequestCustomerIdIsEmpty() {
        CreateWishlistPayload.Request requestPayload = new CreateWishlistPayload.Request("");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(BAD_REQUEST.value())
                .body("detail.customerId", equalTo("must not be empty"));
    }

    @Test
    public void shouldAddProductToWishlist() {
        AddWishlistProductPayload requestPayload = new AddWishlistProductPayload(PRODUCT_ID);
        Wishlist wishlist = new Wishlist(CUSTOMER_ID, 20);
        var saved = wishlistRepository.save(wishlist);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post(BASE_URL + "/" + saved.getId() + "/customer/" + CUSTOMER_ID + "/product")
                .then()
                .statusCode(NO_CONTENT.value());
    }

    @Test
    public void shouldThrowsReachOutLimitException() {
        AddWishlistProductPayload requestPayload = new AddWishlistProductPayload(PRODUCT_ID);
        Wishlist wishlist = new Wishlist(CUSTOMER_ID, 20);
        for (int i = 0; i < maxLimit; i++) {
            wishlist.addProduct(PRODUCT_ID + i);
        }
        var saved = wishlistRepository.save(wishlist);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post(BASE_URL + "/" + saved.getId() + "/customer/" + CUSTOMER_ID + "/product")
                .then()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    public void shouldRemoveProductFromWishlist() {
        Wishlist wishlist = new Wishlist(CUSTOMER_ID, 20);
        wishlist.addProduct(PRODUCT_ID);
        var saved = wishlistRepository.save(wishlist);

        RestAssured.given()
                .when()
                .delete(BASE_URL + "/" + saved.getId() + "/customer/" + CUSTOMER_ID + "/product/" + PRODUCT_ID)
                .then()
                .statusCode(NO_CONTENT.value());
    }

    @Test
    public void shouldGetProductFromWishlist() {
        Wishlist wishlist = new Wishlist(CUSTOMER_ID, 20);
        wishlist.addProduct(PRODUCT_ID);
        var saved = wishlistRepository.save(wishlist);

        RestAssured.given()
                .when()
                .get(BASE_URL + "/" + saved.getId() + "/customer/" + CUSTOMER_ID + "/product/" + PRODUCT_ID)
                .then()
                .statusCode(OK.value())
                .body("customerId", equalTo(CUSTOMER_ID))
                .body("wishlistId", equalTo(saved.getId()))
                .body("productId", equalTo(PRODUCT_ID));
    }

    @Test
    public void shouldReturnNotFoundWhenProductDoesNotExist() {
        Wishlist wishlist = new Wishlist(CUSTOMER_ID, 20);
        var saved = wishlistRepository.save(wishlist);
        RestAssured.given()
                .when()
                .get(BASE_URL + "/" + saved.getId() + "/customer/" + CUSTOMER_ID + "/product/" + PRODUCT_ID)
                .then()
                .statusCode(NOT_FOUND.value())
                .body("detail", equalTo("product not found"));
    }

    @Test
    public void shouldGetAllProductsFromWishlist() {
        Wishlist wishlist = new Wishlist(CUSTOMER_ID, 20);
        wishlist.addProduct(PRODUCT_ID);
        var saved = wishlistRepository.save(wishlist);

        RestAssured.given()
                .when()
                .get(BASE_URL + "/" + saved.getId() + "/customer/" + CUSTOMER_ID + "/product")
                .then()
                .statusCode(OK.value())
                .body("$", hasItem(PRODUCT_ID));
    }

}
