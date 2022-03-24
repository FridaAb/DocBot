package com.gs.docbot.service;

import java.util.HashMap;

import com.gs.docbot.service.impl.DocBotException;
import com.gs.docbot.service.impl.DocBotServiceImpl;
import com.gs.telegramchatbot.AbstractTelegramLongPollingBot;
import com.gs.telegramchatbot.bo.CommandData;
import com.gs.telegramchatbot.bo.TelegramCommand;
import com.gs.telegramchatbot.utils.CommandLineUtils;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DocBotService extends AbstractTelegramLongPollingBot {

    public static final String COMMAND_DOCS = "docs";
    public static final String COMMAND_DOCDOWNLOAD = "docdownload";
    private DocBotServiceImpl docBotServiceImpl;
    private static final Logger logger = LoggerFactory.getLogger(DocBotService.class);

    public DocBotService(
            @Value("${docbot.name}") String botUsername,
            @Value("${docbot.apikey}") String apiKey,
            @Value("${docbot.function}") String botFunction,
            DocBotServiceImpl docBotServiceImpl) {
        this.botUsername = botUsername;
        this.apiKey = apiKey;
        this.botFunction = botFunction;
        this.docBotServiceImpl = docBotServiceImpl;

        initCommands();
    }

    @Override
    public void onUpdateReceived(Update update) {

        TelegramCommand tc = new TelegramCommand(update);
        if (tc.getVerb()==null){
            return;
        }

        switch(tc.getVerb()){
            case COMMAND_DOCS:
                processDocs(update);
                break;
            case COMMAND_DOCDOWNLOAD:
                processDocDownloads(update);
                break;
            case COMMAND_HELP:
                printHelp(tc);
                break;
            default:
                noImplement(tc);
        }
    }

    private void initCommands() {
        CommandData commandDataDocs = CommandData.CommandDataBuilder.commandData()
                .withVerb("/"+COMMAND_DOCS)
                .withOptions(getDocsOptions())
                .withPresentation("get doc list")
                .build();
        CommandData commandDataDownloadDocs = CommandData.CommandDataBuilder.commandData()
                .withVerb("/"+COMMAND_DOCDOWNLOAD)
                .withOptions(getDocDownloadOptions())
                .withPresentation("download a doc")
                .build();
        commands = new HashMap<>();
        commands.put(COMMAND_DOCS, commandDataDocs);
        commands.put(COMMAND_DOCDOWNLOAD, commandDataDownloadDocs);
    }

    private Options getDocDownloadOptions() {
        Options options = new Options();
        Option option = new Option("n", "number", true, "number to download");
        option.setRequired(true);
        options.addOption(option);
        return options;
    }

    private Options getDocsOptions() {
        Options options = new Options();
        Option option = new Option("e", "extension", true, "extensions to get");
        option.setRequired(false);
        options.addOption(option);

        Option option2 = new Option("r", "recursive", false, "recursive search");
        option2.setRequired(false);
        options.addOption(option2);

        Option option3 = new Option("n", "name", true, "filename contains");
        option3.setRequired(false);
        options.addOption(option3);
        return options;
    }

    public void processDocDownloads(Update update) {
        CommandData commandData = commands.get(COMMAND_DOCDOWNLOAD);
        Options options = commandData.getOptions();
        try {
            SendDocument doc = this.docBotServiceImpl.getDoc(new TelegramCommand(update, options));
            this.sendDocument(doc) ;
        } catch (ParseException e) {
            printMessage(new TelegramCommand(update), CommandLineUtils
                    .getHelpCommandLine(commandData.getVerb(), options, commandData.getPresentation()));
        } catch (DocBotException e) {
            printMessage(new TelegramCommand(update), e.getMessage());
        }
    }

    public void processDocs(Update update) {
        CommandData commandData = commands.get(COMMAND_DOCS);
        Options options = commandData.getOptions();
        try {
            this.sendMessage(this.docBotServiceImpl.getDocsName(new TelegramCommand(update, options)));
        } catch (ParseException e) {
            printMessage(new TelegramCommand(update), CommandLineUtils.getHelpCommandLine(commandData.getVerb(), options, commandData.getPresentation()));
        }
    }
}

