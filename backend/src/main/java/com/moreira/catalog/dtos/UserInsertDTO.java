package com.moreira.catalog.dtos;

import com.moreira.catalog.services.validation.UserInsertValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@UserInsertValid
public class UserInsertDTO extends UserDTO {

    private String password;


}
