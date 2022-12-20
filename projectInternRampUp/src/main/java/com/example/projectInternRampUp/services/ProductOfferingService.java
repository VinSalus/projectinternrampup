package com.example.projectInternRampUp.services;

import com.example.projectInternRampUp.dtos.ProductOfferingCharacteristicDto;
import com.example.projectInternRampUp.entities.Characteristic;
import com.example.projectInternRampUp.entities.ProductOffering;
import com.example.projectInternRampUp.repositories.ProductOfferingRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductOfferingService {

    @Autowired
    private ProductOfferingRepository productOfferingRepository;

    @Autowired
    private TimePeriodRepository timePeriodRepository;

    @Autowired
    private CharacteristicService characteristicService;

    public List<ProductOffering> findAll() {
        List<ProductOffering> productOffering = new ArrayList<>();
        List<ProductOffering> productOfferingList = productOfferingRepository.findAll();
        for (ProductOffering p : productOfferingList) {
            if (p.getActive()) {
                productOffering.add(p);
            }
        }
        return productOffering;
    }

    public ProductOffering findById(Integer id) {

        Optional<ProductOffering> obj = productOfferingRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public ProductOffering insert(ProductOfferingCharacteristicDto obj) {
        try {
            ProductOffering productOffering = new ProductOffering();
            productOffering.setName(obj.getName());
            productOffering.setPOState(obj.getPostate());
            productOffering.setSellIndicator(obj.getSellIndicator());

            if (obj.getValidFor().getId() != null) {
                Integer id = obj.getValidFor().getId();
                obj.setValidFor(timePeriodRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(id)));
            } else {
                timePeriodRepository.save(obj.getValidFor());
            }
            productOffering.setValidFor(obj.getValidFor());

            Characteristic characteristicP;

            for (Characteristic characteristic : obj.getCharacteristics()) {
                if (characteristic.getId() != null) {
                    characteristicP = characteristicService.findById(characteristic.getId());
                } else {
                    characteristicP = characteristicService.insert(characteristic);
                }
                productOffering.ReturnCharacteristicList(characteristicP);
            }

            return productOfferingRepository.save(productOffering);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Value cannot be null");
        } catch (ConstraintViolationException e) {
            throw new DatabaseException("Missing data");
        }
    }

    public void delete(Integer id) {
        try {
            ProductOffering productOffering = this.findById(id);
            productOffering.setActive(false);
            productOffering = this.update(id, productOffering);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ProductOffering update(Integer id, ProductOffering obj) {
        try {
            ProductOffering entity = productOfferingRepository.getReferenceById(id);
            updateData(entity, obj);
            return productOfferingRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        } catch (TransactionSystemException e) {
            throw new DatabaseException("TransactionSystemException: Field has empty value or incorrect value");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("DataIntegrityViolationException: Field has empty value or duplicated value");
        } catch (ConstraintViolationException e) {
            throw new DatabaseException("ConstraintViolationException: Field has empty value or incorrect value");
        }
    }

    private void updateData(ProductOffering entity, ProductOffering obj) {
        if (obj.getName().isBlank() || obj.getName().isEmpty()) {
            throw new DatabaseException("Empty value");
        }
        entity.setName(obj.getName());
        if (obj.getValidFor().getId() != null) {
            Integer id = obj.getValidFor().getId();
            obj.setValidFor(timePeriodRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(id)));
        } else {
            timePeriodRepository.save(obj.getValidFor());
        }
        entity.setValidFor(obj.getValidFor());
        entity.setPOState(obj.getPOState());
        entity.setSellIndicator(obj.getSellIndicator());
    }

    public void checkIfIdIsDeleted(ProductOffering obj, Integer id) {
        if (obj.getActive() != true) {
            throw new ResourceNotFoundException(id);
        }
    }
}