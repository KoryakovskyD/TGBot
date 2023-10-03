import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class RandomImageDownloader {
        public static void main(String[] args) {
            getPhoto();
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
