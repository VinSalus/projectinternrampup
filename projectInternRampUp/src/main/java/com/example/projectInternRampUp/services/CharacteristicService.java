package com.example.projectInternRampUp.services;

import com.example.projectInternRampUp.entities.Characteristic;
import com.example.projectInternRampUp.repositories.CharacteristicRepository;
import com.example.projectInternRampUp.repositories.TimePeriodRepository;
import com.example.projectInternRampUp.services.exceptions.DatabaseException;
import com.example.projectInternRampUp.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharacteristicService {

    @Autowired
    private CharacteristicRepository characteristicRepository;

    @Autowired
    private TimePeriodRepository timePeriodRepository;

    public List<Characteristic> findAll() {
        List<Characteristic> characteristic = new ArrayList<>();
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        for (Characteristic c : characteristicList) {
            if (c.getActive()) {
                characteristic.add(c);
            }
        }
        return characteristic;
    }

    public Characteristic findById(Integer id) {
        Optional<Characteristic> obj = characteristicRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    String stringPattern = "[^\\p{P}|^\\d+]+";

    public Characteristic insert(Characteristic obj) {
        try {
            saveValidFor(obj);
            return characteristicRepository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("DataIntegrityViolationException: Field has empty value or duplicated value");
        } catch (ConstraintViolationException e) {
            throw new DatabaseException("ConstraintViolationException: Field has empty value or incorrect value");
        } catch (UnexpectedTypeException e) {
            throw new DatabaseException("UnexpectedTypeException: Field is null or has an incorrect value");
        }
    }

    public void delete(Integer id) {
        try {
            Characteristic characteristic = this.findById(id);
            characteristic.setActive(false);
            characteristic = this.update(id, characteristic);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Characteristic update(Integer id, Characteristic obj) {
        try {
            Characteristic entity = characteristicRepository.getReferenceById(id);
            updateData(entity, obj);
            return characteristicRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        } catch (UnexpectedTypeException e) {
            throw new DatabaseException("Field is null");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Field cannot be null");
        } catch (TransactionSystemException e) {
            throw new DatabaseException("Invalid data");
        }

    }

    private void updateData(Characteristic entity, Characteristic obj) {
        if (obj.getName().isBlank() || obj.getName().isEmpty()) {
            throw new DatabaseException("Empty value");
        }
        entity.setName(obj.getName());
        entity.setCharacteristicType(obj.getCharacteristicType());
        saveValidFor(obj);
        entity.setValidFor(obj.getValidFor());
        if (obj.getValueType().matches(stringPattern)) {
            entity.setValueType(obj.getValueType());
        } else {
            throw new DatabaseException("Field only accepts text");
        }
    }

    public void checkIfIdIsDeleted(Characteristic obj, Integer id) {
        if (obj.getActive() != true) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void saveValidFor(Characteristic obj) {
        if (obj.getValidFor().getId() != null) {
            Integer id = obj.getValidFor().getId();
            obj.setValidFor(timePeriodRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(id)));
        } else {
            timePeriodRepository.save(obj.getValidFor());
        }
    }

}