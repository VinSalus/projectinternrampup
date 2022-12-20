package com.example.projectInternRampUp.services;

import com.example.projectInternRampUp.dtos.OrderDtoFindId;
import com.example.projectInternRampUp.dtos.ProductOfferingDto;
import com.example.projectInternRampUp.entities.Address;
import com.example.projectInternRampUp.entities.Order;
import com.example.projectInternRampUp.entities.OrderItem;
import com.example.projectInternRampUp.entities.ProductOffering;
import com.example.projectInternRampUp.repositories.*;
import com.example.projectInternRampUp.services.exceptions.DatabaseException;
import com.example.projectInternRampUp.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductOfferingService productOfferingService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    public List<Order> findAll() {
        List<Order> order = new ArrayList<>();
        List<Order> orderList = orderRepository.findAll();
        for (Order o : orderList) {
            if (o.getActive()) {
                order.add(o);
            }
        }
        return order;
    }

    public Order findById(Integer id) {
        Optional<Order> obj = orderRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }


    public Order insert(OrderDtoFindId obj) {
        try {
            //insert order
            //bond products with order
            Order order = new Order();
            order.setInstant(obj.getInstant());

            if (obj.getBankSlipPayment() != null) {
                order.setPayment(obj.getBankSlipPayment());
            } else if (obj.getCreditCardPayment() != null) {
                order.setPayment(obj.getCreditCardPayment());
            }
            order.getPayment().setOrder(order);

            Optional<Address> addressObj = addressRepository.findById(obj.getDeliveryAddress().getId());
            Address deliveryAddress = addressObj.orElseThrow(() -> new
                    ResourceNotFoundException(obj.getDeliveryAddress().getId()));

            order.setDeliveryAddress(deliveryAddress);
            order.setClient(customerRepository.findById(obj.getClient().getId()).get()); //client id from postman
            paymentRepository.save(order.getPayment());
            order = orderRepository.save(order);
            OrderItem orderItem;
            ProductOffering productOffering;

            for (ProductOfferingDto offeringDto : obj.getProducts()) {
                productOffering = productOfferingService.findById(offeringDto.getId());
                orderItem = new OrderItem(order, productOffering, offeringDto.getUnitPrice(), offeringDto.getDiscount(),
                        offeringDto.getQuantity());


                orderItemRepository.save(orderItem);
                order.getItems().add(orderItem);
            }

            return order;

        } catch (ConstraintViolationException e) {
            throw new DatabaseException("ConstraintViolationException: Field has empty value or incorrect value" + e.getLocalizedMessage());
        } catch (NullPointerException e) {
            throw new DatabaseException("NullPointerException: Field has empty value or incorrect value");
        } catch (TransactionSystemException e) {
            throw new DatabaseException("TransactionSystemException: Field has empty value or incorrect value");
        }

    }

    public void delete(Integer id) {
        try {
            Order order = this.findById(id);
            order.setActive(false);
            orderRepository.save(order);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Order update(Integer id, OrderDtoFindId obj) {
        try {
            Order entity = orderRepository.getReferenceById(id);
            updateData(entity, obj);
            return orderRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Order entity, OrderDtoFindId obj) {
        if (obj.getBankSlipPayment() != null) {
            paymentRepository.deleteById(entity.getId());
            entity.setPayment(obj.getBankSlipPayment());
            entity.getPayment().setOrder(entity);
            paymentRepository.save(entity.getPayment());
        } else if (obj.getCreditCardPayment() != null) {
            paymentRepository.deleteById(entity.getId());
            entity.setPayment(obj.getCreditCardPayment());
            entity.getPayment().setOrder(entity);
            paymentRepository.save(entity.getPayment());
        }
    }

    public void checkIfIdIsDeleted(Order obj, Integer id) {
        if (obj.getActive() != true) {
            throw new ResourceNotFoundException(id);
        }
    }
}