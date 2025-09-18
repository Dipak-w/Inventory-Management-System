package com.example.inventory.util;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class JsoupSanitizer {
    // sanitize input to remove scripts and unwanted HTML
    public String sanitize(String input) {
        if (input == null) return null;
        return Jsoup.clean(input, Safelist.simpleText());
    }
}