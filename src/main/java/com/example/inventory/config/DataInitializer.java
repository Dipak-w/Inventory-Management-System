package com.example.inventory.config;
import com.example.inventory.model.Role;
import com.example.inventory.model.User;
import com.example.inventory.repository.RoleRepository;
import com.example.inventory.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;

@Configuration
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public DataInitializer(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        this.roleRepo = roleRepo; this.userRepo = userRepo; this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
//        Role admin = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_ADMIN")));
//        Role user = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_USER")));
//
//        if (!userRepo.existsByUsername("admin")) {
//            User adminUser = new User();
//            adminUser.setUsername("admin");
//            adminUser.setPassword(encoder.encode("admin")); // CHANGE for prod or seed via env
//            adminUser.setRoles(Set.of(admin, user));
//            userRepo.save(adminUser);
//        }
    }
}
