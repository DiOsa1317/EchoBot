package com.example.echobot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Модульные тесты для проверки бизнес-логики EchoService.
 */
public class EchoServiceTest {

    private EchoService echoService;

    @BeforeEach
    void setUp() {
        echoService = new EchoService();
    }

    @ParameterizedTest(name = "Вход: '{0}'")
    @ValueSource(strings = {
            "Привет",
            "Cтрока с пробелами",
            "До переноса строки\nПеред табуляцией\tПосле неё"
    })
    @DisplayName("prepareBotResponse возвращает идентичный входной текст")
    void shouldReturnExactSameText(String input) {
        String result = echoService.prepareBotResponse(input);
        assertEquals(input, result, "Метод должен вернуть ТОЧНО тот же текст");
    }

    @Test
    @DisplayName("prepareBotResponse обрабатывает пустую строку")
    void shouldHandleEmptyString() {
        String result = echoService.prepareBotResponse("");
        assertEquals("Сообщение не должно быть пустым", result);
    }

    @Test
    @DisplayName("prepareBotResponse обрабатывает строку из пробелов")
    void shouldHandleBlankString() {
        String result = echoService.prepareBotResponse("   \t\n  ");
        assertEquals("Сообщение не должно быть пустым", result);
    }
}