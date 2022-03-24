package com.gs.coem.kafkaoperativo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.gs.coem.telegram.MessageBot;

@RestController
@Controller
public class envioMensajes {

	@GetMapping("/prueba")
	@ResponseBody
	public String validacion() {
		
		String res;
		
		try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MessageBot());
            res= "Enviado";
        } 
		catch (TelegramApiException e) {
            e.printStackTrace();
            res= "Error";
        }
		return res;
	}
}
