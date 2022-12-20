package com.example.projectInternRampUp.services;

import com.example.projectInternRampUp.entities.Role;
import com.example.projectInternRampUp.entities.User;
import com.example.projectInternRampUp.repositories.UserRepository;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public List<User> findAll() {
        List<User> user = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for (User u : userList) {
            if (u.getActive()) {
                user.add(u);
            }
        }
        return user;
    }

    public User findById(Integer id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Autowired
    private PasswordEncoder passwordEnconder;


    public User insert(User obj) {
        try {
            if (obj.getRoles().isEmpty()) {
                Role role = roleService.findById(2);
                obj.getRoles().add(role);
            } else {
                List<Role> roles = new ArrayList<>();
                for (Role role : obj.getRoles()) {
                    roles.add(roleService.findById(role.getId()));
                }
                obj.getRoles().clear();
                for (Role role : roles) {
                    obj.getRoles().add(role);
                }
            }
            checkIfPasswordIsEmpty(obj);
            obj.setPassword(passwordEnconder.encode(obj.getPassword()));
            return userRepository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Email already exists");
        } catch (ConstraintViolationException e) {
            throw new DatabaseException("Empty field" + e.getConstraintViolations());
        }
    }

    public void delete(Integer id) {
        try {
            User user = this.findById(id);
            user.setActive(false);
            user = this.update(id, user);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }


    public User update(Integer id, User obj) {
        try {
            User entity = userRepository.getReferenceById(id);
            checkIfPasswordIsEmpty(obj);
            updateData(entity, obj);
            return userRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        } catch (TransactionSystemException e) {
            throw new DatabaseException("Invalid email");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Empty value or duplicated value");
        } catch (ConstraintViolationException e) {
            throw new DatabaseException("Empty field");
        }

    }

    private void updateData(User entity, User obj) {
        entity.setEmail(obj.getEmail());
        passwordEnconder = new BCryptPasswordEncoder();
        entity.setPassword(passwordEnconder.encode(obj.getPassword()));
    }

    public void checkIfPasswordIsEmpty(User obj) {
        if (obj.getPassword().isBlank() || obj.getPassword().isEmpty()) {
            throw new DatabaseException("Empty value");
        }
    }

    public void checkIfIdIsDeleted(User obj, Integer id) {
        if (obj.getActive() != true) {
            throw new ResourceNotFoundException(id);
        }
    }

}