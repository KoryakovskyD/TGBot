
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import static org.apache.http.HttpHeaders.USER_AGENT;


public class Bot extends TelegramLongPollingBot {

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        setButtons(sendMessage);
        sendMessage(sendMessage);
    }

    private void sendMessage(SendMessage sendMessage) {

    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();


        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "Чем могу помочь?");
                    break;
                case "/setting":
                    sendMsg(message, "Что будем настраивать?");
                    break;
                case "/pic":

                    SendPhoto messagePhoto = null;
                    messagePhoto = new SendPhoto();
                    //this.execute(messagePhoto);
                    
                    break;
                default:
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    private File sendGet() throws Exception {

            String url = "https://loremflickr.com/320/240";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Значение по умолчанию - GET
            con.setRequestMethod("GET");

            // Добавляем заголовок запроса
            con.setRequestProperty("User-Agent", USER_AGENT);
            //con.setRequestProperty("Accept-Charset", "UTF-8");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);



            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();



            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            File file = new File(url, "E:\\kek.jpg");



            return file;

            // Распечатываем результат
           // System.out.println(response.toString());

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
        return "New_kor_bot";
    }

    @Override
    public String getBotToken() {
        return "6439342104:AAHSYQbVl14RIWRwfcDijg0bt_ffDNRX0IE";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
