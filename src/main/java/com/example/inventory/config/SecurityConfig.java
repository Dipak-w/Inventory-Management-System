package com.example.inventory.config;
import com.example.inventory.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final com.example.inventory.config.JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(com.example.inventory.config.JwtUtils jwtUtils, UserDetailsServiceImpl uds) {
        this.jwtUtils = jwtUtils; this.userDetailsService = uds;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        com.example.inventory.config.JwtAuthFilter jwtFilter = new com.example.inventory.config.JwtAuthFilter(jwtUtils, userDetailsService);

        http
                .csrf(csrf -> csrf.disable()) // stateless API; CSRF not needed for JWT
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).maxAgeInSeconds(31536000))
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, ex2) -> res.sendError(401, "Unauthorized"))
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
