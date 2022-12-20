package com.example.projectInternRampUp.resources;

import com.example.projectInternRampUp.dtos.CharacteristicDto;
import com.example.projectInternRampUp.entities.Characteristic;
import com.example.projectInternRampUp.services.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/characteristics")
public class CharacteristicResource {

    @Autowired
    private CharacteristicService service;

    @GetMapping
    public ResponseEntity<List<CharacteristicDto>> findAll() {
        List<Characteristic> list = service.findAll();
        List<CharacteristicDto> listDto = list.stream().map(obj -> new CharacteristicDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Characteristic> findbyId(@PathVariable Integer id) {
        Characteristic obj = service.findById(id);
        service.checkIfIdIsDeleted(obj, id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Characteristic> insert(@RequestBody Characteristic obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Characteristic> update(@PathVariable Integer id, @RequestBody Characteristic obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}