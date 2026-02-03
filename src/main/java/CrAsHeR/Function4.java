package CrAsHeR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class Function4 {
    private static final Scanner scanner = new Scanner(System.in);

    public static void execute() {
        System.out.println("\u001B[31mВведите IP адрес или домен: \u001B[0m");
        String target = scanner.nextLine();

        try {
            String apiUrl = "http://ip-api.com/json/" + target + "?fields=status,message,query,country,regionName,city,zip,timezone,isp,org,as,reverse";
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());

            if (json.getString("status").equals("success")) {
                String ipRange = getIpRange(json.getString("query"));

                System.out.println("\u001B[31m==============================\u001B[0m");
                System.out.println("\u001B[31mIP адрес: \u001B[0m" + json.getString("query"));
                System.out.println("\u001B[31mИмя ресурса: \u001B[0m" + json.getString("reverse"));
                System.out.println("\u001B[31mIP диапазон: \u001B[0m" + ipRange);
                System.out.println("\u001B[31mПровайдер: \u001B[0m" + json.getString("isp"));
                System.out.println("\u001B[31mОрганизация: \u001B[0m" + json.getString("org"));
                System.out.println("\u001B[31mСтрана: \u001B[0m" + json.getString("country"));
                System.out.println("\u001B[31mРегион: \u001B[0m" + json.getString("regionName"));
                System.out.println("\u001B[31mГород: \u001B[0m" + json.getString("city"));
                System.out.println("\u001B[31mЧасовой пояс: \u001B[0m" + json.getString("timezone"));
                System.out.println("\u001B[31mМестное время: \u001B[0m" + java.time.ZonedDateTime.now(java.time.ZoneId.of(json.getString("timezone"))));
                System.out.println("\u001B[31mИндекс: \u001B[0m" + json.getString("zip"));
                System.out.println("\u001B[31m==============================\u001B[0m");
            } else {
                System.out.println("Ошибка: " + json.getString("message"));
            }

        } catch (Exception e) {
            System.out.println("Произошла ошибка при получении данных.");
            e.printStackTrace();
        }
    }

    private static String getIpRange(String ip) {
        try {
            String apiUrl = "https://ipinfo.io/" + ip + "/json";
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            return json.has("org") ? json.getString("org") : "Не найдено";

        } catch (Exception e) {
            return "Ошибка получения диапазона";
        }
    }
}
