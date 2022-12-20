package com.example.projectInternRampUp.repositories;
import com.example.projectInternRampUp.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}