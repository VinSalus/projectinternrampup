package com.example.projectInternRampUp.resources;

import com.example.projectInternRampUp.entities.Role;
import com.example.projectInternRampUp.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RoleResource {

    @Autowired
    private RoleService service;

    @GetMapping
    public ResponseEntity<List<Role>> findAll() {
        List<Role> list = service.findAll();
        return ResponseEntity.ok().body(list);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Role> findbyId(@PathVariable Integer id) {
        Role obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Role> insert(@RequestBody Role obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Role> update(@PathVariable Integer id, @RequestBody Role obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}