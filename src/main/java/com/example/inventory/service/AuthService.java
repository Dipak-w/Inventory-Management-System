package com.example.inventory.service;
import com.example.inventory.dto.AuthRequest;
import com.example.inventory.dto.AuthResponse;
import com.example.inventory.dto.RegisterRequest;
import com.example.inventory.model.Role;
import com.example.inventory.model.User;
import com.example.inventory.repository.RoleRepository;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.config.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userRepo = userRepo; this.roleRepo = roleRepo; this.encoder = encoder; this.jwtUtils = jwtUtils;
    }

    public AuthResponse login(AuthRequest req) {
        User user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            log.warn("Failed login for user {}", req.getUsername());
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtils.generateToken(user.getUsername());
        log.info("User {} logged in", req.getUsername());
        return new AuthResponse(token);
    }

    public void register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        Role userRole = roleRepo.findByName("ROLE_USER")
                .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_USER")));
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRoles(Collections.singleton(userRole));
        userRepo.save(u);
        log.info("Registered user {}", req.getUsername());
    }
}
