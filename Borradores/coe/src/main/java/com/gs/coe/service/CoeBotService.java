package com.gs.coe.service;

import com.gs.coe.service.impl.CoeBotServiceImpl;
import com.gs.telegramchatbot.AbstractTelegramLongPollingBot;
import com.gs.telegramchatbot.bo.CommandData;
import com.gs.telegramchatbot.bo.TelegramCommand;
import com.gs.telegramchatbot.utils.CommandLineUtils;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Service
public class CoeBotService extends AbstractTelegramLongPollingBot {

    private CoeBotServiceImpl coeBotServiceImpl;
    public static final String HOLA = "hola";
    public static final String ADIOS = "adios";
    public static final String CONSULTA = "consulta";

    public CoeBotService(
            @Value("${coebot.name}") String botUsername,
            @Value("${coebot.apikey}") String apiKey,
            @Value("${coebot.function}") String botFunction,

            CoeBotServiceImpl coeBotServiceImpl) {
        this.botUsername = botUsername;
        this.apiKey = apiKey;
        this.botFunction = botFunction;
        this.coeBotServiceImpl = coeBotServiceImpl;
        initCommands();
    }

    private void initCommands() {
        CommandData hola = CommandData.CommandDataBuilder.commandData()
                .withVerb("/hola")
                .withOptions(null)
                .withPresentation("Saludo del bot")
                .build();

        CommandData adios = CommandData.CommandDataBuilder.commandData()
                .withVerb("/adios")
                .withOptions(null)
                .withPresentation("Despedida del bot")
                .build();

        CommandData consulta = CommandData.CommandDataBuilder.commandData()
                .withVerb("/consulta")
                .withOptions(null)
                .withPresentation("Motivo de la consulta")
                .build();

        commands = new HashMap<>();
        commands.put(HOLA, hola);
        commands.put(ADIOS, adios);
        commands.put(CONSULTA, consulta);

    }

    @Override
    public void onUpdateReceived(Update update) {
        TelegramCommand tc = new TelegramCommand( update);
        if (tc.getVerb()==null){
            initCommands();
            return;
        }

        switch(tc.getVerb()){
            case ADIOS:
                ADIOS(update);
                break;
            case HOLA:
                HOLA(update);
                break;
            case COMMAND_HELP:
                printHelp(tc);
                break;
            case CONSULTA:
                CONSULTA(update);
            default:
                noImplement(tc);
        }

    }

    private void HOLA(Update update) {
        CommandData commandData = commands.get(HOLA);
        try {
            this.sendMessage((this.coeBotServiceImpl.hola(new TelegramCommand(update, commandData.getOptions()))));
        } catch (ParseException e) {
            printMessage(new TelegramCommand(update), CommandLineUtils
                    .getHelpCommandLine(commandData.getVerb(), commandData.getOptions(), commandData.getPresentation()));
        }
    }

    private void ADIOS(Update update) {
        CommandData commandData = commands.get(ADIOS);
        try {
            this.sendMessage((this.coeBotServiceImpl.adios(new TelegramCommand(update, commandData.getOptions()))));
        } catch (ParseException e) {
            printMessage(new TelegramCommand(update), CommandLineUtils
                    .getHelpCommandLine(commandData.getVerb(), commandData.getOptions(), commandData.getPresentation()));
        }
    }

    private void CONSULTA(Update update) {
        CommandData commandData = commands.get(CONSULTA);
        try {
            this.sendMessage((this.coeBotServiceImpl.adios(new TelegramCommand(update, commandData.getOptions()))));
        } catch (ParseException e) {
            printMessage(new TelegramCommand(update), CommandLineUtils
                    .getHelpCommandLine(commandData.getVerb(), commandData.getOptions(), commandData.getPresentation()));
        }
    }
}
