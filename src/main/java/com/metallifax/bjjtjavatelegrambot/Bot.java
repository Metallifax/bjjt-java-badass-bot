package com.metallifax.bjjtjavatelegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {
    String BOT_TOKEN;
    String BOT_USERNAME;

    Bot(@Value("${bot.BOT_TOKEN}") String BOT_TOKEN, @Value("${bot.BOT_USERNAME}") String BOT_USERNAME) {
        this.BOT_TOKEN = BOT_TOKEN;
        this.BOT_USERNAME = BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setReplyToMessageId(update.getMessage().getMessageId());
            message.setText(update.getMessage().getText());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
