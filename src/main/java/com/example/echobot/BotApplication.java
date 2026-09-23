package com.example.echobot;

import com.example.echobot.bots.TelegramBot;
import com.example.echobot.service.EchoService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Точка входа в приложение TelegramBot.
 * Отвечает за загрузку конфигурации из локального файла,
 * создание экземпляра бота и его регистрацию в Telegram API
 * через механизм Long Polling.
 */
public class BotApplication {

    /**
     * Имя файла с локальной конфигурацией (токен и имя бота).
     */
    private static final String CONFIGURATION_FILE_NAME = "config.properties";

    /**
     * Ключ для получения токена авторизации из свойств конфигурации.
     */
    private static final String KEY_BOT_TOKEN = "bot.token";

    /**
     * Ключ для получения имени пользователя бота (username) из свойств конфигурации.
     */
    private static final String KEY_BOT_USERNAME = "bot.username";

    /**
     * Главный метод приложения. Запускает процесс инициализации и регистрации бота.
     *
     * @param args аргументы командной строки (в текущей реализации не используются)
     */
    public static void main(String[] args) {
        try {
            var config = loadConfiguration();
            validateConfig(config);

            var echoService = new EchoService();
            startTelegramBot(config, echoService);
        } catch (Exception e) {
            System.err.println("Ошибка при запуске: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Инициализирует и регистрирует Telegram-бота в API.
     *
     * @param config      свойства конфигурации, содержащие токен и username
     * @param echoService сервис для обработки текста сообщений
     * @throws TelegramApiException если произошла ошибка при регистрации бота
     */
    private static void startTelegramBot(Properties config, EchoService echoService)
            throws TelegramApiException {
        var token = config.getProperty(KEY_BOT_TOKEN);
        var username = config.getProperty(KEY_BOT_USERNAME);

        var bot = new TelegramBot(token, username, echoService);

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);

        System.out.println("Telegram-бот '" + username + "' успешно подключен.");
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

    /**
     * Проверяет наличие обязательных параметров в конфигурации.
     *
     * @param config загруженные свойства конфигурации
     * @throws IllegalStateException если отсутствует токен или имя бота
     */
    private static void validateConfig(Properties config) {
        if (config.getProperty(KEY_BOT_TOKEN) == null ||
                config.getProperty(KEY_BOT_USERNAME) == null) {
            throw new IllegalStateException(
                    "В файле " + CONFIGURATION_FILE_NAME + " отсутствуют токен (bot.token) или имя бота (bot.username)"
            );
        }
    }
}