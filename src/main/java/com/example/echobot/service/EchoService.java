package com.example.echobot.service;

/**
 * Сервис для обработки входящих сообщений.
 */
public class EchoService implements IService {

    /**
     * Возвращает исходный текст сообщения или уведомление, если текст пуст.
     *
     * @param messageText исходный текст от пользователя
     * @return обработанный текст
     */
    public String prepareBotResponse(String messageText) {
        if (messageText.isBlank()) {
            return "Сообщение не должно быть пустым";
        }
        return messageText;
    }
}