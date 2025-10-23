package com.pluralsight;

import jakarta.enterprise.context.*;

import java.util.Random;

@ApplicationScoped
public class IsbnGenerator {
    public String generateIsbn() {
        return "13-84356-" + Math.abs(new Random().nextInt());
    }
}
