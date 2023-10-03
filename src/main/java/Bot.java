
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import static org.apache.http.HttpHeaders.USER_AGENT;


public class Bot extends TelegramLongPollingBot {

    public void sendMsg(Long who, String text) {
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .text(text).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException();
        }
    }

    private void sendMessage(SendMessage sendMessage) {

    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        var user = message.getFrom();


        if (message.hasText() && update.getMessage().hasText()) {
            switch (message.getText()) {

                case "/check":
                    try {

                        sendMsg(user.getId(), "Отчет программы checkdelivery");
                        SendDocument sendDocument = new SendDocument();
                        sendDocument.setChatId(user.getId());


                        File file = new File("E:\\check.txt");
                        sendDocument.setDocument(new InputFile(file));

                        sendDocument.setCaption("Отчет");
                        execute(sendDocument);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case "/help":
                    sendMsg(user.getId(), "Чем могу помочь?");
                    break;
                case "/setting":
                    sendMsg(user.getId(), "Что будем настраивать?");
                    break;
                case "/pic":

                    try {
                        FileUtils.cleanDirectory(new File("E:\\photo"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    getPhoto();


                    try {

                        File dir = new File("E:\\photo");
                        File[] arrFiles = dir.listFiles();
                        List<File> lst = Arrays.asList(arrFiles);

                        int i = 0;
                        for (File file : lst) {
                            i++;
                            BufferedImage img = ImageIO.read(file);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(img, "jpg", baos);
                            SendPhoto sendPhoto = new SendPhoto();
                            sendPhoto.setChatId(user.getId());
                            sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(baos.toByteArray()), "photo.jpg"));
                            sendPhoto.setCaption("Your sexy girl number " + i);
                            execute(sendPhoto);
                            Thread.sleep(3000);
                        }

                    } catch (IOException | TelegramApiException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowsList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/setting"));
        keyboardFirstRow.add(new KeyboardButton("/pic"));

        keyboardRowsList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    public static void getPhoto() {
        String downloadFolder = "E:\\photo";
        int numImages = 5;


        boolean folderCreated = new java.io.File(downloadFolder).mkdirs();
        if (folderCreated) {
            System.out.println("Папка для сохранения картинок создана: " + downloadFolder);
        } else {
            System.out.println("Папка для сохранения картинок уже существует: " + downloadFolder);
        }


        for (int i = 1; i <= numImages; i++) {
            String imageUrl = "https://api.dujin.org/pic/";

            try (InputStream in = new URL(imageUrl).openStream();
                 OutputStream out = new FileOutputStream(downloadFolder + "/image" + i + ".jpg")) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                System.out.println("Картинка " + i + " загружена успешно!");

            } catch (IOException e) {
                System.out.println("Ошибка загрузки картинки " + i + ": " + e.getMessage());
            }
        }

        System.out.println("Загрузка картинок завершена!");
    }
}
