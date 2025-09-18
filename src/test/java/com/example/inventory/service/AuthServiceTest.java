package com.example.inventory.service;
import com.example.inventory.config.JwtUtils;
import com.example.inventory.dto.AuthRequest;
import com.example.inventory.dto.RegisterRequest;
import com.example.inventory.repository.RoleRepository;
import com.example.inventory.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock UserRepository userRepo;
    @Mock RoleRepository roleRepo;
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    JwtUtils jwtUtils = new JwtUtils("TestSecretKeyChangeMe1234567890", 3600000);
    AuthService sut;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        sut = new AuthService(userRepo, roleRepo, encoder, jwtUtils);
    }

    @Test
    void register_existingUsername_throws() {
        RegisterRequest r = new RegisterRequest();
        r.setUsername("exists"); r.setPassword("p");
        when(userRepo.existsByUsername("exists")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> sut.register(r));
    }

    @Test
    void login_invalidCredentials_throws() {
        AuthRequest req = new AuthRequest();
        req.setUsername("john"); req.setPassword("pass");
        when(userRepo.findByUsername("john")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> sut.login(req));
    }
}
