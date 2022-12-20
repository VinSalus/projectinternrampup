package com.example.projectInternRampUp.entities;

import com.example.projectInternRampUp.dtos.CharacteristicDto;
import com.example.projectInternRampUp.enumerations.POState;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_productoffering")
public class ProductOffering implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    @NotNull
    @ManyToOne
    private TimePeriod validFor;
    @NotNull
    private Integer postate;
    @NotNull
    private Boolean sellIndicator;

    @ManyToMany
    @NotEmpty
    @JoinTable(name = "tb_characteristic_productoffering",
            joinColumns = @JoinColumn(name = "product_offering_id"),
            inverseJoinColumns = @JoinColumn(name = "characteristic_id"))
    private List<Characteristic> characteristics = new ArrayList<>();

    @OneToMany(mappedBy = "id.productOffering")
    private Set<OrderItem> items = new HashSet<>();

    private Boolean isActive = true;

    public ProductOffering() {

    }

    public ProductOffering(Integer id, String name, TimePeriod validFor, POState postate, Boolean sellIndicator, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.validFor = validFor;
        setPOState(postate);
        this.sellIndicator = sellIndicator;
        this.isActive = isActive;
    }

    //Lombok causes the application to crash if used in this entity that's why the getters and setters are manually set.

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimePeriod getValidFor() {
        return validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }

    public Boolean getSellIndicator() {
        return sellIndicator;
    }

    public void setSellIndicator(Boolean sellIndicator) {
        this.sellIndicator = sellIndicator;
    }

    public List<CharacteristicDto> getCharacteristic() {
        List<CharacteristicDto> characteristicDto = characteristics.stream().map(obj -> new CharacteristicDto(obj)).collect(Collectors.toList());
        return characteristicDto;
    }

    public void setPOState(POState postate) {
        if (postate != null) {
            this.postate = postate.getCode();
        }
    }

    public POState getPOState() {
        return POState.valueOf(postate);
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void ReturnCharacteristicList(Characteristic obj) {
        characteristics.add(obj);
    }

    @JsonIgnore
    public Set<Order> getOrders() {
        Set<Order> set = new HashSet<>();
        for (OrderItem x : items) {
            set.add(x.getOrder());
        }
        return set;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellIndicator, validFor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductOffering other = (ProductOffering) obj;
        return Objects.equals(sellIndicator, other.sellIndicator) && Objects.equals(validFor, other.validFor);
    }


}