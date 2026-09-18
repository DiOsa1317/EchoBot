package com.example.echobot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Точка входа в приложение EchoBot.
 * Отвечает за загрузку конфигурации из локального файла,
 * создание экземпляра бота и его регистрацию в Telegram API
 * через механизм Long Polling.
 */
public class BotApplication {

    /** Имя файла с локальной конфигурацией (токен и имя бота). */
    private static final String CONFIGURATION_FILE_NAME = "config.properties";

    /** Ключ для получения токена авторизации из свойств конфигурации. */
    private static final String KEY_BOT_TOKEN = "bot.token";

    /** Ключ для получения имени пользователя бота (username) из свойств конфигурации. */
    private static final String KEY_BOT_USERNAME = "bot.username";

    /**
     * Главный метод приложения. Запускает процесс инициализации и регистрации бота.
     *
     * @param args аргументы командной строки (в текущей реализации не используются)
     */
    public static void main(String[] args) {
        try {
            var config = loadConfiguration();
            var token = config.getProperty(KEY_BOT_TOKEN);
            var username = config.getProperty(KEY_BOT_USERNAME);

            if (token == null || token.isBlank()
                    || username == null || username.isBlank()) {
                throw new IllegalStateException(
                        "Ошибка конфигурации: токен или имя бота не найдены в файле " + CONFIGURATION_FILE_NAME
                );
            }

            var botApi = new TelegramBotsApi(DefaultBotSession.class);
            var echoBot = new EchoBot(token, username);
            botApi.registerBot(echoBot);

            System.out.println("Бот '" + username + "' успешно запущен.");

        } catch (TelegramApiException e) {
            System.err.println("Ошибка Telegram API: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Ошибка чтения конфигурации: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Загружает свойства из файла config.properties, расположенного в корне проекта.
     * Использует try-with-resources для автоматического закрытия потока ввода.
     *
     * @return объект Properties с загруженными данными конфигурации
     * @throws IOException если файл не найден, недоступен для чтения или поврежден
     */
    private static Properties loadConfiguration() throws IOException {
        var properties = new Properties();

        try (InputStream input = new FileInputStream(CONFIGURATION_FILE_NAME)) {
            properties.load(input);
        }

        return properties;
    }
}