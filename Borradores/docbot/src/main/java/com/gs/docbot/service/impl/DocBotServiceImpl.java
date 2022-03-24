package com.gs.docbot.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.gs.docbot.service.DocBotService;
import com.gs.telegramchatbot.bo.TelegramCommand;
import com.gs.telegramchatbot.utils.EmojiCtes;
import com.gs.telegramchatbot.utils.EmojiService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Service
public class DocBotServiceImpl {
    public static final int BUTTONS_NUM_COLUMNS = 3;
    private final String pathPublicDir;
    private final List<File> listFiles = new ArrayList<>();
    private final EmojiService emojiService;
    private List < List <InlineKeyboardButton>> rowsInline;

    public DocBotServiceImpl(@Value("${docbot.publicdir}") String pathPublicDir, EmojiService emojiService) {
        this.pathPublicDir = pathPublicDir;
        this.emojiService = emojiService;
    }

    public SendMessage getDocsName(TelegramCommand telegramCommand){
        String[] extensions = null;
        String nameContains = null;
        boolean recursive = false;

        rowsInline = new ArrayList < > ();

        if (telegramCommand.getParams().hasOption("e")){
            extensions = StringUtils.commaDelimitedListToStringArray(telegramCommand.getParams().getOptionValue("e"));
        }
        if (telegramCommand.getParams().hasOption("n")){
            nameContains = telegramCommand.getParams().getOptionValue("n");
        }
        if (telegramCommand.getParams().hasOption("r")){
            recursive = true;
        }

        var listFilesAux = new ArrayList<>(FileUtils.listFiles(
                new File(this.pathPublicDir),
                extensions, recursive));
        StringBuilder out = new StringBuilder();
        int i = 0;
        listFiles.clear();
        if (nameContains == null){
            listFiles.addAll(listFilesAux);
        }
        for(var file:listFilesAux ){
            if (nameContains==null){
                out.append(String.format("%d . %s%n", i, file.getName()));
                addButton(i);
                i++;
            }else{
                if (file.getName().toLowerCase().contains(nameContains.toLowerCase())){
                    out.append(String.format("%d . %s%n", i, file.getName()));
                    addButton(i);
                    i++;
                    listFiles.add(file);
                }
            }
        }
        if (out.length()==0){
            return telegramCommand.sendMessage("empty dir", false);
        }else{
            SendMessage sendMessage = telegramCommand.sendMessage(out.toString(), false);
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            markupInline.setKeyboard(rowsInline);
            sendMessage.setReplyMarkup(markupInline);

            return sendMessage;
        }
    }

    private void addButton(int i) {
        InlineKeyboardButton inline = new InlineKeyboardButton();
        inline.setText(emojiService.getEmojiUnicode(EmojiCtes.FILE_CABINET) + " " + i);
        inline.setCallbackData("/" + DocBotService.COMMAND_DOCDOWNLOAD + " -n " + i);
        if ((i % BUTTONS_NUM_COLUMNS) == 0){
            rowsInline.add(new ArrayList < > ());
        }
        rowsInline.get(rowsInline.size()-1).add(inline);
    }

    public SendDocument getDoc(TelegramCommand telegramCommand) throws DocBotException {

        int number;
        try{
            number = Integer.parseInt(telegramCommand.getParams().getOptionValue("n"));
        }catch(NumberFormatException e){
            throw new DocBotException("You must provide a valid number");
        }
        if (number > this.listFiles.size() || number < 0){
            throw new DocBotException("You must provide a valid number");
        }

        InputFile inputFile = new InputFile();
        inputFile.setMedia(this.listFiles.get(number));

        return telegramCommand.sendDoc(inputFile);
    }
}

