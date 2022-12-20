package com.example.projectInternRampUp.resources;

import com.example.projectInternRampUp.dtos.ProductOfferingCharacteristicDto;
import com.example.projectInternRampUp.entities.ProductOffering;
import com.example.projectInternRampUp.services.ProductOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/productOfferings")
public class ProductOfferingResource {

    @Autowired
    private ProductOfferingService service;

    @GetMapping
    public ResponseEntity<List<ProductOfferingCharacteristicDto>> findAll() {
        List<ProductOffering> list = service.findAll();
        List<ProductOfferingCharacteristicDto> listDto = list.stream().map(obj -> new ProductOfferingCharacteristicDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductOffering> findById(@PathVariable Integer id) {
        ProductOffering obj = service.findById(id);
        service.checkIfIdIsDeleted(obj, id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductOffering> insert(@RequestBody ProductOfferingCharacteristicDto obj) {
        ProductOffering productOfferingObj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productOfferingObj.getId()).toUri();
        return ResponseEntity.created(uri).body(productOfferingObj);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<ProductOffering> update(@PathVariable Integer id, @RequestBody ProductOffering obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}