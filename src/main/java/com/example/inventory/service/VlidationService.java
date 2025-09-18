package com.example.inventory.service;
import com.example.inventory.util.JsoupSanitizer;
import org.springframework.stereotype.Service;

@Service
class ValidationService {
    private final JsoupSanitizer sanitizer;


    public ValidationService(JsoupSanitizer sanitizer) {
        this.sanitizer = sanitizer;
    }

    public String sanitize(String input) {
        return sanitizer.sanitize(input);
    }
}