package com.gs.coe.service.impl;

import com.gs.telegramchatbot.bo.TelegramCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class CoeBotServiceImpl {

    public SendMessage hola(TelegramCommand telegramCommand){
        return telegramCommand.sendMessage("Hola, soy MessageBot, bienvenido al Chat", false);
    }

    public SendMessage adios(TelegramCommand telegramCommand){
        return telegramCommand.sendMessage("Un placer conocerte, hasta luego!", false);
    }

    public SendMessage consulta(TelegramCommand telegramCommand){
        return telegramCommand.sendMessage("¿Cuál es el motivo de tu consulta?", false);
    }
}
