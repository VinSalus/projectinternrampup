package com.example.projectInternRampUp.resources;

import com.example.projectInternRampUp.dtos.OrderDto;
import com.example.projectInternRampUp.dtos.OrderDtoFindId;
import com.example.projectInternRampUp.entities.Order;
import com.example.projectInternRampUp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAll() {
        List<Order> list = service.findAll();
        List<OrderDto> listDto = list.stream().map(obj -> new OrderDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') || authentication.principal ==  @orderRepository.findById(#id).get().userAssociated()")
    public ResponseEntity<Order> findById(@PathVariable Integer id) {
        Order obj = service.findById(id);
        service.checkIfIdIsDeleted(obj, id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @PreAuthorize("authentication.principal == @customerRepository.findById(#obj.getClient().getId()).get().orderAssociated()")
    public ResponseEntity<Order> insert(@RequestBody OrderDtoFindId obj) {
        Order orderObj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(orderObj.getId()).toUri();
        return ResponseEntity.created(uri).body(orderObj);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') || authentication.principal ==  @orderRepository.findById(#id).get().userAssociated()")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("authentication.principal ==  @orderRepository.findById(#id).get().userAssociated()")
    public ResponseEntity<Order> update(@PathVariable Integer id, @RequestBody OrderDtoFindId obj) {
        Order orderPatch = service.update(id, obj);
        return ResponseEntity.ok().body(orderPatch);
    }
}