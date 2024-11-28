package com.dhruv.controller;

import com.dhruv.exception.AddressException;
import com.dhruv.exception.UserException;
import com.dhruv.model.Address;
import com.dhruv.model.User;
import com.dhruv.request.AddressRequest;
import com.dhruv.service.AddressService;
import com.dhruv.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    /**
     * Add a new address for the authenticated user.
     *
     * @param jwt    Authorization token.
     * @param request AddressRequest payload containing address details.
     * @return ResponseEntity with the created Address.
     * @throws UserException if the user cannot be found.
     */
    @PostMapping
    public ResponseEntity<Address> addAddress(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AddressRequest request) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Address newAddress = new Address();
        newAddress.setFirstName(request.getFirstName());
        newAddress.setLastName(request.getLastName());
        newAddress.setStreetAddress(request.getStreetAddress());
        newAddress.setCity(request.getCity());
        newAddress.setState(request.getState());
        newAddress.setZipCode(request.getZipCode());
        newAddress.setMobile(request.getMobile());
        newAddress.setUser(user);

        Address createdAddress = addressService.saveAddress(newAddress);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    /**
     * Update an existing address for the authenticated user.
     *
     * @param jwt    Authorization token.
     * @param id     ID of the address to update.
     * @param request AddressRequest payload containing updated details.
     * @return ResponseEntity with the updated Address.
     * @throws AddressException if the address cannot be found or does not belong to the user.
     * @throws UserException    if the user cannot be found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id,
            @RequestBody AddressRequest request) throws AddressException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Address updatedAddress = new Address();
        updatedAddress.setFirstName(request.getFirstName());
        updatedAddress.setLastName(request.getLastName());
        updatedAddress.setStreetAddress(request.getStreetAddress());
        updatedAddress.setCity(request.getCity());
        updatedAddress.setState(request.getState());
        updatedAddress.setZipCode(request.getZipCode());
        updatedAddress.setMobile(request.getMobile());

        Address result = addressService.updateAddress(user.getId(), id, updatedAddress);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Get all addresses for the authenticated user.
     *
     * @param jwt Authorization token.
     * @return ResponseEntity with a list of Address objects.
     * @throws UserException if the user cannot be found.
     * @throws AddressException if no addresses are found.
     */
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses(
            @RequestHeader("Authorization") String jwt) throws UserException, AddressException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Address> addresses = addressService.findAllAddressesByUserId(user.getId());
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    /**
     * Delete an address by ID for the authenticated user.
     *
     * @param jwt Authorization token.
     * @param id  ID of the address to delete.
     * @return ResponseEntity with no content.
     * @throws AddressException if the address cannot be found or does not belong to the user.
     * @throws UserException    if the user cannot be found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws AddressException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        addressService.deleteAddress(user.getId(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
