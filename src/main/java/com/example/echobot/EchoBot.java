package com.example.echobot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Основной класс эхо-бота для Telegram.
 * Наследует TelegramLongPollingBot и реализует логику повторения
 * текстовых сообщений пользователей в режиме Long Polling.
 */
public class EchoBot extends TelegramLongPollingBot {

    /** Токен авторизации бота, полученный от BotFather. */
    private String botToken;

    /** Имя пользователя (username) бота без символа @. */
    private String botUsername;

    /**
     * Создает новый экземпляр эхо-бота.
     *
     * @param token токен авторизации бота
     * @param name  имя пользователя бота (username)
     */
    public EchoBot(String token, String name) {
        this.botToken = token;
        this.botUsername = name;
    }

    /**
     * Обрабатывает входящие обновления от Telegram API.
     * Реализует логику эхо-бота: повторяет текстовые сообщения пользователя.
     *
     * @param update объект обновления, содержащий данные о событии
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        var messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        var message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);

        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает имя пользователя (username) данного бота.
     * Требуется родительским классом для идентификации при подключении к API.
     *
     * @return username бота
     */
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    /**
     * Возвращает токен авторизации данного бота.
     * Требуется родительским классом для аутентификации запросов к Telegram API.
     *
     * @return токен бота
     */
    @Override
    public String getBotToken() {
        return botToken;
    }
}