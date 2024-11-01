package com.dhruv.controller;

import com.dhruv.exception.CartItemException;
import com.dhruv.exception.UserException;
import com.dhruv.model.CartItem;
import com.dhruv.model.User;
import com.dhruv.service.CartItemService;
import com.dhruv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart_item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CartItem> addCartItem(
            @RequestBody CartItem cartItem,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        cartItem.setUserId(user.getId()); // Set user ID from JWT
        CartItem createdCartItem = cartItemService.createCartItem(cartItem);
        return new ResponseEntity<>(createdCartItem, HttpStatus.CREATED);
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItem> getCartItemById(
            @PathVariable Long cartItemId) throws CartItemException {
        CartItem cartItem = cartItemService.findCartItemById(cartItemId);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

//    @GetMapping
//    @Operation(description = "Get All Cart Items For User")
//    public ResponseEntity<List<CartItem>> getAllCartItems(
//            @RequestHeader("Authorization") String jwt) throws UserException {
//        User user = userService.findUserProfileByJwt(jwt);
//        List<CartItem> cartItems = cartItemService.findAllCartItemsByUserId(user.getId());
//        return new ResponseEntity<>(cartItems, HttpStatus.OK);
//    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(
            @RequestBody CartItem cartItem,
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}