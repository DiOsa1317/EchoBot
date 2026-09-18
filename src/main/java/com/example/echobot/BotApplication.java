package com.example.echobot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BotApplication {

    private static final String CONFIGURATION_FILE_NAME = "config.properties";
    private static final String KEY_BOT_TOKEN = "bot.token";
    private static final String KEY_BOT_USERNAME = "bot.username";

    public static void main(String[] args) {
        try {
            var config = loadConfiguration();
            var token = config.getProperty(KEY_BOT_TOKEN);
            var username = config.getProperty(KEY_BOT_USERNAME);

            if (token == null || token.isBlank()
                    || username == null || username.isBlank()) {
                throw new IllegalStateException("Ошибка конфигурации: токен или имя не найдены в файле");
            }

            var botApi = new TelegramBotsApi(DefaultBotSession.class);
            var echoBot = new EchoBot(token, username);
            botApi.registerBot(echoBot);

        } catch (TelegramApiException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    private static Properties loadConfiguration() throws IOException {
        var properties = new Properties();

        try (InputStream input = new FileInputStream(CONFIGURATION_FILE_NAME)) {
            properties.load(input);
        }
        return properties;
    }
}
