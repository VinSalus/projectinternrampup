package com.example.projectInternRampUp.repositories;
import com.example.projectInternRampUp.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}