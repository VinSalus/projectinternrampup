package com.example.projectInternRampUp.entities;

import com.example.projectInternRampUp.dtos.AddressDto;
import com.example.projectInternRampUp.dtos.OrderDto;
import com.example.projectInternRampUp.dtos.UserDto;
import com.example.projectInternRampUp.enumerations.CustomerType;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Table(name = "tb_customer")
@Getter
@Setter
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String customerName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Column(unique = true)
    private String documentNumber;
    @NotNull
    @NotEmpty
    @NotBlank
    private String customerStatus;
    @NotNull
    private Integer customerType;
    @NotNull
    @NotEmpty
    @NotBlank
    private String creditScore;
    @NotNull
    @NotEmpty
    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @OneToOne
    @JoinColumn(unique = true)
    @NotNull
    private User user;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)

    @Column(unique = true)
    private Set<Order> orders = new HashSet<>();

    @NotNull
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();

    private Boolean isActive = true;

    public Customer() {

    }

    public Customer(Integer id, String customerName, String documentNumber, String customerStatus,
                    CustomerType customerType, String creditScore, String password, User user, Boolean isActive) {
        this.id = id;
        this.customerName = customerName;
        this.documentNumber = documentNumber;
        this.customerStatus = customerStatus;
        setCustomerType(customerType);
        this.creditScore = creditScore;
        this.password = password;
        this.user = user;
        this.isActive = isActive;
    }

    public UserDto getUser() {
        UserDto userDto = new UserDto(user);
        return userDto;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDto> getOrders() {
        List<OrderDto> ordersDto = orders.stream().map(obj -> new OrderDto(obj)).collect(Collectors.toList());
        return ordersDto;
    }

    public List<AddressDto> getAddresses() {
        List<AddressDto> addressesDto = addresses.stream().map(obj -> new AddressDto(obj)).collect(Collectors.toList());
        return addressesDto;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void setCustomerType(CustomerType customerType) {
        if (customerType != null) {
            this.customerType = customerType.getCode();
        }
    }

    public CustomerType getCustomerType() {
        return CustomerType.valueOf(customerType);
    }

    public Boolean getActive() {
        return isActive;
    }

    public String orderAssociated() {
        return user.getEmail();
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentNumber, user);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        return Objects.equals(documentNumber, other.documentNumber) && Objects.equals(user, other.user);
    }
}