package CrAsHeR.proxy;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ProxyClient {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static String proxyUrl;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   PROXY CLIENT - ГЛАВНЫЙ ПК        ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println();
        
        System.out.print("[>] IP Raspberry Pi: ");
        String ip = scanner.nextLine().trim();
        proxyUrl = "http://" + ip + ":8080";
        
        while (true) {
            System.out.println();
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║ [1] Отправить команду              ║");
            System.out.println("║ [2] Проверить статус               ║");
            System.out.println("║ [3] Выход                          ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("[>] Выбор: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    sendCommand(scanner);
                    break;
                case "2":
                    checkStatus();
                    break;
                case "3":
                    return;
            }
        }
    }
    
    private static void sendCommand(Scanner scanner) {
        System.out.println();
        System.out.println("[1] Спам вебхуками");
        System.out.println("[2] Спам ботом");
        System.out.println("[3] Кик всех");
        System.out.println("[4] Бан всех");
        System.out.println("[5] Удалить каналы");
        System.out.print("[>] Команда: ");
        String cmd = scanner.nextLine().trim();
        
        System.out.print("[>] Токен бота: ");
        String token = scanner.nextLine().trim();
        
        System.out.print("[>] ID сервера: ");
        String guildId = scanner.nextLine().trim();
        
        String command = cmd + "|" + token + "|" + guildId;
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(proxyUrl + "/command"))
                .POST(HttpRequest.BodyPublishers.ofString(command))
                .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("[✓] Команда отправлена на сервер!");
            }
        } catch (Exception e) {
            System.out.println("[✗] Ошибка: " + e.getMessage());
        }
    }
    
    private static void checkStatus() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(proxyUrl + "/status"))
                .GET()
                .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("[*] Статус: " + response.body());
        } catch (Exception e) {
            System.out.println("[✗] Ошибка: " + e.getMessage());
        }
    }
}
