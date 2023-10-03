import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class Picture {
    public static String getPicture() throws IOException {
        //URL url = new URL("https://api.dujin.org/pic/");

        //URL url = new URL("https://www.avalon.ru/Retraining/Profile/");

        //HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        //connection.setRequestProperty("accept", "application/json");
       // connection.addRequestProperty("User-Agent", "Mozilla");

        //InputStream stream = connection.getInputStream();
       // String response = new Scanner(stream).next();

        String rez = downloadWebPage("https://habr.com/ru/post/447322/");

        return rez;

        //System.out.println("rez=" + response);

        //String imageUrl = url.toString();
       // String destinationFile = "image.jpg";

       // saveImage(imageUrl, destinationFile);
    }


    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    private static String downloadWebPage(String url) throws IOException {

        StringBuilder result = new StringBuilder();
        String line;

        // add user agent
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla");
        urlConnection.setReadTimeout(5000);
        urlConnection.setConnectTimeout(5000);

        try (InputStream is = new URL(url).openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

        }

        return result.toString();

    }

}
