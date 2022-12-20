package com.example.projectInternRampUp.services;

import com.example.projectInternRampUp.entities.Role;
import com.example.projectInternRampUp.repositories.RoleRepository;
import com.example.projectInternRampUp.services.exceptions.DatabaseException;
import com.example.projectInternRampUp.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<Role> findAll() {
        return repository.findAll();
    }

    public Role findById(Integer id) {
        Optional<Role> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Role insert(Role obj) {
        return repository.save(obj);
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Role update(Integer id, Role obj) {
        try {
            Role entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Role entity, Role obj) {
        entity.setAuthority(obj.getAuthority());
    }
}