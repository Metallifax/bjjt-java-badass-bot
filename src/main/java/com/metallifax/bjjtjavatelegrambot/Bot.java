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
        String command = update.getMessage().getText();

        if (command.equals("/start") || command.equals("/help") || command.equals("/commands")) {
            String helpMessage
                    = "Here's what BJJT Badass Bot can do!\n\n/start /help /commands : Displays this message!\n" +
                    "/hello : Says a greeting!";

            try {
                execute(createTextMessage(update, helpMessage));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (command.equals("/hello")) {
            String greeting = "Hello, " + update.getMessage().getFrom().getFirstName() + "!";

            try {
                execute(createTextMessage(update, greeting));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private SendMessage createTextMessage(Update update, String message) {
        SendMessage response = new SendMessage();
        response.setChatId(update.getMessage().getChatId().toString());
        response.setText(message);
        return response;
    }
}
