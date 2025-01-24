package com.moreira.catalog.services;

import com.moreira.catalog.dtos.NewPasswordDTO;
import com.moreira.catalog.dtos.RecoverAccountDTO;
import com.moreira.catalog.entities.PasswordRecover;
import com.moreira.catalog.entities.User;
import com.moreira.catalog.repositories.PasswordRecoverRepository;
import com.moreira.catalog.repositories.UserRepository;
import com.moreira.catalog.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void createRecoverToken(RecoverAccountDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        String token = UUID.randomUUID().toString();

        PasswordRecover entity = new PasswordRecover();
        entity.setEmail(dto.getEmail());
        entity.setToken(token);
        entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
        passwordRecoverRepository.save(entity);

        String text = "Acesse o link para definir uma nova senha:\n\n" + recoverUri + token + ". Validade de " + tokenMinutes + " minutos.";

        emailService.sendEmail(dto.getEmail(), "Recuperação de senha", text);
    }

    public void saveNewPassword(@Valid NewPasswordDTO dto) {

        List<PasswordRecover> result = passwordRecoverRepository.searchValidToken(dto.getToken(), Instant.now());

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Token inválido");
        }

        User user = userRepository.findByEmail(result.getFirst().getEmail()).get();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

}
