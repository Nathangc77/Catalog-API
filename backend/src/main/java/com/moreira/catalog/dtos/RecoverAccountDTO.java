package com.moreira.catalog.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RecoverAccountDTO {

    @NotBlank(message = "Campo obrigatório")
    @Email(message = "Email inválido")
    private String email;
}
