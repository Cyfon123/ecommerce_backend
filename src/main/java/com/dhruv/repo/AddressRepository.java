package com.dhruv.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhruv.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
