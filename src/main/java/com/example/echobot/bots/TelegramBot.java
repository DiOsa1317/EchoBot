package com.example.echobot.bots;

import com.example.echobot.service.EchoService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Основной класс эхо-бота для Telegram.
 * Наследует TelegramLongPollingBot и реализует логику повторения
 * текстовых сообщений пользователей в режиме Long Polling.
 */
public class TelegramBot extends TelegramLongPollingBot {

    /**
     * Токен авторизации бота, полученный от BotFather.
     */
    private final String botToken;

    /**
     * Имя пользователя (username) бота без символа @.
     */
    private final String botUsername;

    /**
     * Сервис для бизнес-логики обработки сообщений.
     */
    private final EchoService echoService;

    /**
     * Создает новый экземпляр эхо-бота.
     *
     * @param token       токен авторизации бота
     * @param name        имя пользователя бота (username)
     * @param echoService сервис для подготовки ответа
     */
    public TelegramBot(String token, String name, EchoService echoService) {
        this.botToken = token;
        this.botUsername = name;
        this.echoService = echoService;
    }

    /**
     * Обрабатывает входящие обновления от Telegram API.
     * Реализует логику эхо-бота: повторяет текстовые сообщения пользователя.
     *
     * @param update объект обновления, содержащий данные о событии
     */
    @Override
    public void onUpdateReceived(Update update) {
        // Игнорируем обновления, не содержащие текстовых сообщений
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        var messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        String responseText = echoService.prepareBotResponse(messageText);

        var message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(responseText);

        try {
            execute(message);
        } catch (Exception e) {
            System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
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