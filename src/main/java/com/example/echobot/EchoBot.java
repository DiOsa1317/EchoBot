package com.example.echobot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EchoBot extends TelegramLongPollingBot {

    private String botToken;
    private String botUsername;

    public EchoBot(String token, String name) {
        this.botToken = token;
        this.botUsername = name;
    }

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

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
