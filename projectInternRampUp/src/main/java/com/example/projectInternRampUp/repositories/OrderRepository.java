package com.example.projectInternRampUp.repositories;
import com.example.projectInternRampUp.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}