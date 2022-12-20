package com.example.projectInternRampUp.repositories;
import com.example.projectInternRampUp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}