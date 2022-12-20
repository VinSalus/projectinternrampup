package com.example.projectInternRampUp.repositories;
import com.example.projectInternRampUp.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}