package src.main.java.com.telerikacademy.infrastructure.selenium.utils;

import com.github.javafaker.Faker;

import java.time.Instant;
import java.util.HashSet;
import java.util.Random;

public class DataGenerator {

    public static HashSet<String> usedUsername = new HashSet<>();
    public static HashSet<String> usedPassword = new HashSet<>();
    public static HashSet<String> usedEmail = new HashSet<>();
    private static Faker faker = new Faker();
    private static Random random = new Random();

    public static String generateUniqueUsername() {
        String username;
        do {
            username = faker.regexify("[a-zA-Z]{6,20}");
        } while (usedUsername.contains(username));
        usedUsername.add(username);
        return username;
    }

    public static String generateUniquePassword() {
        String password;
        do {
            password = faker.regexify("[a-zA-Z0-9!@#$%^&*()_?<>.,]{6,20}");
        } while (usedPassword.contains(password));
        usedPassword.add(password);
        return password;
    }

    public static String generateUniqueEmail() {
        String email;
        do {
            int number = random.nextInt(1000);
            email = "testaccount." + number + "@yahoo.com";
        } while (usedEmail.contains(email));
        usedEmail.add(email);
        return email;
    }

    public static String generateUniqueContentPost() {
        return faker.lorem().characters(10, 50);
    }

    public static String generateUniqueContentPost(int length) {
        return faker.lorem().characters(length);
    }

    private static String generateString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            result.append(characters.charAt(randomIndex));
        }
        return result.toString();
    }

    public static String generateUniqueContentComment() {
        return "Test Comment - " + Instant.now().toEpochMilli();
    }
}
