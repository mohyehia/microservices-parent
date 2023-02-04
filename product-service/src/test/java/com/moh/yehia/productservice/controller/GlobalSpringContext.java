package com.moh.yehia.productservice.controller;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Locale;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public abstract class GlobalSpringContext {
    @Autowired
    MockMvc mockMvc;

    static Faker faker;

    @BeforeAll
    static void initializeFaker() {
        faker = new Faker(Locale.ENGLISH);
    }
}
