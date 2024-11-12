package com.dhruv.controller;

import com.dhruv.exception.ProductException;
import com.dhruv.exception.UserException;
import com.dhruv.model.Cart;
import com.dhruv.model.User;
import com.dhruv.request.AddItemRequest;
import com.dhruv.service.CartService;
import com.dhruv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping
//    @Operation(description = "Create a new cart for the user")
    public ResponseEntity<Cart> createCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.createCart(user);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PostMapping("/add")
//    @Operation(description = "Add an item to the user's cart")
    public ResponseEntity<String> addCartItem(
            @RequestBody AddItemRequest req,
            @RequestHeader("Authorization") String jwt) throws ProductException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        String response = cartService.addCartItem(user.getId(), req);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @GetMapping
//    @Operation(description = "Get the user's cart")
    public ResponseEntity<Cart> getUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}