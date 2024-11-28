package com.dhruv.service;

import java.util.List;

import com.dhruv.exception.AddressException;
import com.dhruv.exception.UserException;
import com.dhruv.model.Address;

public interface AddressService {
    Address saveAddress(Address address);

    Address updateAddress(Long userId, Long id, Address address) throws AddressException, UserException;

    Address findAddressById(Long id) throws AddressException;

    List<Address> findAllAddressesByUserId(Long userId) throws AddressException;

    void deleteAddress(Long userId, Long id) throws AddressException;
}
