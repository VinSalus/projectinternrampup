package com.example.projectInternRampUp.dtos;

import com.example.projectInternRampUp.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
public class UserDto implements Serializable {

    ///This Dto handles what informations are displayed on a get all Users request.

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    public UserDto() {

    }

    public UserDto(User obj) {
        super();
        this.id = obj.getId();
        this.email = obj.getEmail();
        this.password = obj.getPassword();
    }
}