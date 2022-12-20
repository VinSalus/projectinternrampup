package com.example.projectInternRampUp.services;

import com.example.projectInternRampUp.dtos.AddressDto;
import com.example.projectInternRampUp.dtos.CustomerPostDto;
import com.example.projectInternRampUp.dtos.OrderDtoFindId;
import com.example.projectInternRampUp.entities.Address;
import com.example.projectInternRampUp.entities.Customer;
import com.example.projectInternRampUp.entities.Order;
import com.example.projectInternRampUp.repositories.AddressRepository;
import com.example.projectInternRampUp.repositories.CustomerRepository;
import com.example.projectInternRampUp.services.exceptions.DatabaseException;
import com.example.projectInternRampUp.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    public List<Customer> findAll() {
        List<Customer> customer = new ArrayList<>();
        List<Customer> customerList = customerRepository.findAll();
        for (Customer c : customerList) {
            if (c.getActive()) {
                customer.add(c);
            }
        }
        return customer;
    }


    public Customer findById(Integer id) {
        Optional<Customer> obj = customerRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private PasswordEncoder passwordEnconder;
    String stringPattern = "[^\\p{P}|^\\d+]+";

    public Customer insert(CustomerPostDto obj) {
        try {
            Customer customer = new Customer();
            customer.setCustomerName(obj.getCustomerName());
            customer.setDocumentNumber(obj.getDocumentNumber());
            customer.setCustomerStatus(obj.getCustomerStatus());
            customer.setCustomerType(obj.getCustomerType());
            if (obj.getCreditScore().matches(stringPattern)) {
                customer.setCreditScore(obj.getCreditScore());
            } else {
                throw new DatabaseException("Field only accepts text");
            }
            checkIfPasswordIsEmpty(obj);
            passwordEnconder = new BCryptPasswordEncoder();
            customer.setPassword(passwordEnconder.encode(obj.getPassword()));
            customer.setUser(userService.findById(obj.getUser().getId()));

            Order order;
            customerRepository.save(customer);
            if (obj.getAddresses().isEmpty()) {
                throw new DatabaseException("Client address id is empty");
            }
            for (Address address : obj.getAddresses()) {
                addressRepository.save(address);
                customer.addAddress(address);
            }
            for (OrderDtoFindId orderDtoFindId : obj.getOrders()) {
                order = orderService.insert(orderDtoFindId);
                customer.addOrder(order);
            }
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Field has empty value or incorrect value");
        } catch (ConstraintViolationException e) {
            throw new DatabaseException("Field has empty value or incorrect value");
        } catch (TransactionSystemException e) {
            throw new DatabaseException("Field has empty value or incorrect value");
        }
    }

    public void delete(Integer id) {
        try {
            Customer customer = this.findById(id);
            customer.setActive(false);
            customer = this.update(id, customer);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Customer update(Integer id, Customer obj) {
        try {
            Customer entity = customerRepository.getReferenceById(id);
            if (obj.getPassword().isBlank() || obj.getPassword().isEmpty()) {
                throw new DatabaseException("Empty value");
            }
            if (obj.getDocumentNumber().isBlank() || obj.getDocumentNumber().isEmpty()) {
                throw new DatabaseException("Empty value");
            }
            updateData(entity, obj);
            return customerRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("customerName or documentNumber is null, check if documentNumber is valid");
        }
    }

    private void updateData(Customer entity, Customer obj) {
        entity.setCustomerName(obj.getCustomerName());

        if (!obj.getDocumentNumber().matches(stringPattern)) {
            entity.setDocumentNumber(obj.getDocumentNumber());
        } else {
            throw new DatabaseException("Field only accepts numbers");
        }
        if (obj.getCustomerStatus().matches(stringPattern)) {
            entity.setCustomerStatus(obj.getCustomerStatus());
        } else {
            throw new DatabaseException("Field only accepts text");
        }
        if (obj.getCreditScore().matches(stringPattern)) {
            entity.setCreditScore(obj.getCreditScore());
        } else {
            throw new DatabaseException("Field only accepts text");
        }
        updateTimePeriod(entity, obj);
        entity.setCustomerType(obj.getCustomerType());
        passwordEnconder = new BCryptPasswordEncoder();
        entity.setPassword(passwordEnconder.encode(obj.getPassword()));
    }

    public void checkIfPasswordIsEmpty(CustomerPostDto obj) {
        if (obj.getPassword().isBlank() || obj.getPassword().isEmpty()) {
            throw new DatabaseException("Empty value");
        }
    }

    public void checkIfIdIsDeleted(Customer obj, Integer id) {
        if (obj.getActive() != true) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void updateTimePeriod(Customer entity, Customer obj) {
        List<Address> addressList = new ArrayList<>();
        Integer counter = 0;
        for (AddressDto addressDto : entity.getAddresses()) {
            Address address = addressRepository.findById(addressDto.getId()).get();
            addressList.add(address);
            counter += 1;
        }
        entity.getAddresses().clear();
        if (obj.getAddresses().size() <= counter) {
            for (int i = 0; i < counter; i++) {
                Address index = addressList.get(i);
                AddressDto address = obj.getAddresses().get(i);
                index.setStreet(address.getStreet());
                index.setHouseNumber(address.getHouseNumber());
                index.setNeighborhood(address.getNeighborhood());
                index.setComplement(address.getComplement());
                index.setZipCode(address.getZipCode());
                index.setAddressType(address.getAddressType());
                index.setCountry(address.getCountry());
                addressRepository.save(index);
                entity.addAddress(index);
            }
        } else {
            for (int i = 0; i < counter; i++) {
                Address index = addressList.get(i);
                AddressDto address = obj.getAddresses().get(i);
                index.setStreet(address.getStreet());
                index.setHouseNumber(address.getHouseNumber());
                index.setNeighborhood(address.getNeighborhood());
                index.setComplement(address.getComplement());
                index.setZipCode(address.getZipCode());
                index.setAddressType(address.getAddressType());
                index.setCountry(address.getCountry());
                addressRepository.save(index);
                entity.addAddress(index);
            }
            for (int i = counter; i < obj.getAddresses().size(); i++) {
                AddressDto addressDto = obj.getAddresses().get(i);
                Address address = new Address(null, addressDto.getStreet(), addressDto.getHouseNumber(), addressDto.getNeighborhood(),
                        addressDto.getComplement(), addressDto.getZipCode(), addressDto.getAddressType(), addressDto.getCountry(), entity);
                addressRepository.save(address);
                entity.addAddress(address);
            }
        }
    }
}