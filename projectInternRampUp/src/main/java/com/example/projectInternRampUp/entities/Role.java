package com.example.projectInternRampUp.entities;

import com.example.projectInternRampUp.enumerations.Authorities;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_role")
@Getter
@Setter
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @NotNull
    @Column(unique = true)
    private Integer authority;

    public Role() {

    }

    public Role(Integer id, Authorities authority) {
        this.id = id;
        setAuthority(authority);
    }

    public void setAuthority(Authorities authority) {
        if (authority != null) {
            this.authority = authority.getCode();
        }
    }

    public Authorities getAuthority() {
        return Authorities.valueOf(authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authority);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        return authority == other.authority;
    }
}