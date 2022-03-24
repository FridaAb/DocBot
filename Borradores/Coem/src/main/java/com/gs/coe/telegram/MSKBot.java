package com.gs.coe.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MSKBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(final Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());

            try {
                if (message.getText().equals("Hola"))
                {
                    message.setText("Bienvenido al chat, soy CoeMs_Bot");
                    execute(message);
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "MyMSK_bot";
    }

    @Override
    public String getBotToken() {
        return "5122862539:AAGB9eo4k6PpaPwyFFjok0JgJ5PJYlmNm7s";
    }
}