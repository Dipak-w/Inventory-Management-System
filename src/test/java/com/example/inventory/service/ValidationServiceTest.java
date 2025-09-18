package com.example.inventory.service;
import com.example.inventory.util.JsoupSanitizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {
    ValidationService sut;

    @BeforeEach
    void setup() {
        sut = new ValidationService(new JsoupSanitizer());
    }

    @Test
    void sanitize_removesScript() {
        String input = "<script>alert(1)</script>Hello <b>World</b>";
        String out = sut.sanitize(input);
        assertFalse(out.toLowerCase().contains("<script>"));
        assertTrue(out.contains("Hello"));
    }
}
