package com.dhruv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhruv.exception.AddressException;
import com.dhruv.model.Address;
import com.dhruv.repo.AddressRepository;
import com.dhruv.service.AddressService;

@Service
public class AddressServiceImplementation implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long userId, Long id, Address updatedAddress) throws AddressException {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new AddressException("Address with ID " + id + " not found."));
        
        // Assuming each address has a userId for validation
        if (!existingAddress.getUser().getId().equals(userId)) {
            throw new AddressException("Unauthorized access to update address.");
        }

        existingAddress.setFirstName(updatedAddress.getFirstName());
        existingAddress.setLastName(updatedAddress.getLastName());
        existingAddress.setStreetAddress(updatedAddress.getStreetAddress());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setZipCode(updatedAddress.getZipCode());
        existingAddress.setMobile(updatedAddress.getMobile());
        
        return addressRepository.save(existingAddress);
    }

    @Override
    public Address findAddressById(Long id) throws AddressException {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressException("Address with ID " + id + " not found."));
    }

    @Override
    public List<Address> findAllAddressesByUserId(Long userId) throws AddressException {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()) {
            throw new AddressException("No addresses found for user with ID " + userId);
        }
        return addresses.stream()
                .filter(address -> address.getUser().getId().equals(userId))
                .toList();
    }

    @Override
    public void deleteAddress(Long userId, Long id) throws AddressException {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressException("Address with ID " + id + " not found."));

        if (!address.getUser().getId().equals(userId)) {
            throw new AddressException("Unauthorized access to delete address.");
        }

        addressRepository.delete(address);
    }
}
