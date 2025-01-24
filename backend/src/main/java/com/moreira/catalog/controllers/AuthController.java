package com.moreira.catalog.controllers;

import com.moreira.catalog.dtos.NewPasswordDTO;
import com.moreira.catalog.dtos.RecoverAccountDTO;
import com.moreira.catalog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping(value = "/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody RecoverAccountDTO dto) {
        service.createRecoverToken(dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/new-password")
    public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody NewPasswordDTO dto) {
        service.saveNewPassword(dto);
        return ResponseEntity.noContent().build();
    }
}
