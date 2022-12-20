package com.example.projectInternRampUp.entities;

import com.example.projectInternRampUp.dtos.CustomerDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Email
    @NotNull
    @NotEmpty
    @NotBlank
    @Column(unique = true)
    private String email;
    @NotNull
    @NotEmpty
    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    private String password;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Customer client;

    @NotNull
    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            )
    )
    private Set<Role> roles = new HashSet<>(); //one or unlimited instances of roles

    private Boolean isActive = true;

    public User() {

    }

    public User(Integer id, String email, String password, Boolean isActive) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }

    public CustomerDto getClient() {
        if (client != null) {
            CustomerDto clientDto = new CustomerDto(client);
            return clientDto;
        } else {
            return null;
        }
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, roles);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return Objects.equals(email, other.email) && Objects.equals(roles, other.roles);
    }


}