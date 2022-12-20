package com.example.projectInternRampUp.resources;

import com.example.projectInternRampUp.dtos.CustomerDto;
import com.example.projectInternRampUp.dtos.CustomerPostDto;
import com.example.projectInternRampUp.entities.Customer;
import com.example.projectInternRampUp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {

    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAll() {
        List<Customer> list = service.findAll();
        List<CustomerDto> listDto = list.stream().map(obj -> new CustomerDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);

    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') || authentication.principal == @customerRepository.findById(#id).get().getUser().getEmail()")
    public ResponseEntity<Customer> findById(@PathVariable Integer id) {
        Customer obj = service.findById(id);
        service.checkIfIdIsDeleted(obj, id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @PreAuthorize("authentication.principal == @userRepository.findById(#obj.getUser().getId()).get().getEmail()")
    public ResponseEntity<Customer> insert(@RequestBody CustomerPostDto obj) {
        Customer objCustomer = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(objCustomer);
    }

    @PreAuthorize("authentication.principal == @customerRepository.findById(#id).get().getUser().getEmail()")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("authentication.principal == @customerRepository.findById(#id).get().getUser().getEmail()")
    public ResponseEntity<Customer> update(@PathVariable Integer id, @RequestBody Customer obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}