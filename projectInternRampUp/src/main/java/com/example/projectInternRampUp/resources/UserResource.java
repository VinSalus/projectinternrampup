package com.example.projectInternRampUp.resources;

import com.example.projectInternRampUp.dtos.UserDto;
import com.example.projectInternRampUp.entities.User;
import com.example.projectInternRampUp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor


public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> findAll() {
        List<User> list = service.findAll();
        List<UserDto> listDto = list.stream().map(obj -> new UserDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/users/{id}")
    @PreAuthorize("hasRole('ADMIN') || authentication.principal == @userRepository.findById(#id).get().getEmail()")
    public ResponseEntity<User> findById(@PathVariable @Valid Integer id) {
        User obj = service.findById(id);
        service.checkIfIdIsDeleted(obj, id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping(value = "/users")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> insert(@RequestBody @Valid User obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> signup(@RequestBody @Valid User obj) {
        obj.getRoles().clear();
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasRole('ADMIN') || authentication.principal == @userRepository.findById(#id).get().getEmail()")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/users/{id}")
    @PreAuthorize("hasRole('ADMIN') || authentication.principal == @userRepository.findById(#id).get().getEmail()")
    public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}