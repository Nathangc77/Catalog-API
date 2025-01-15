package com.moreira.catalog.dtos;

import com.moreira.catalog.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDTO {

    private Long id;

    @NotBlank(message = "Required field")
    private String firstName;
    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(User entity) {
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();

        entity.getRoles().forEach(x -> this.roles.add(new RoleDTO(x)));
    }
}
