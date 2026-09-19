package com.example.echobot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Модульные тесты для проверки бизнес-логики EchoBot.
 * Тестирует метод prepareBotResponse на корректность возврата текста
 * и устойчивость к граничным значениям (null, спецсимволы).
 */
public class EchoBotTest {

    /**
     * Экземпляр тестируемого бота.
     */
    private EchoBot echoBot;

    /**
     * Инициализирует новый экземпляр EchoBot перед каждым тестом.
     * Использует фиктивные токен и имя, так как реальная авторизация не требуется.
     */
    @BeforeEach
    void setUp() {
        echoBot = new EchoBot("test_token", "test_name");
    }

    /**
     * Проверяет, что метод возвращает входной текст без изменений
     * для различных допустимых значений (обычный текст, пробелы, переносы строк, максимальная длина).
     *
     * @param input входная строка, передаваемая параметризованным тестом
     */
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {
            "Привет",
            "Cтрока с пробелами",
            "До переноса строки\nПеред табуляцией\tПосле неё",
            "MAX_TEXT_LENGTH_MESSAGEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    })
    @DisplayName("prepareBotResponse возвращает идентичный входной текст")
    void shouldReturnExactSameText(String input) {
        String result = echoBot.prepareBotResponse(input);

        assertEquals(input, result,
                "Метод должен вернуть ТОЧНО тот же текст без изменений");
    }

    /**
     * Проверяет, что метод безопасно обрабатывает null и не выбрасывает исключений.
     * Это защита от возможных сбоев API или некорректных данных извне.
     */
    @Test
    @DisplayName("prepareBotResponse безопасно обрабатывает null")
    void shouldHandleNullInput() {
        assertDoesNotThrow(() -> echoBot.prepareBotResponse(null));
    }
}
