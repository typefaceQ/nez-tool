package CrAsHeR;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.entities.UserSnowflake;

import java.awt.Color;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Function5 {
    public static final String COLOR_RED = "\u001b[31m";
    public static final String COLOR_GREEN = "\u001b[32m";
    public static final String COLOR_YELLOW = "\u001b[33m";
    public static final String COLOR_RESET = "\u001b[0m";

    private static JDA jda;
    private static Scanner scanner = new Scanner(System.in);
    private static Guild selectedGuild;

    public static void execute() {
        printHeader();
        String token = getToken();
        
        if (initializeBot(token)) {
            selectServer();
            showMainMenu();
        }
    }

    private static void printHeader() {
        System.out.println(COLOR_RED + "╔════════════════════════════════════╗");
        System.out.println("║     DISCORD BOT - PANEL            ║");
        System.out.println("╚════════════════════════════════════╝" + COLOR_RESET);
        System.out.println();
    }

    private static String getToken() {
        System.out.print(COLOR_YELLOW + "[>] Введите токен бота: " + COLOR_RESET);
        return scanner.nextLine().trim();
    }

    private static boolean initializeBot(String token) {
        try {
            System.out.println(COLOR_YELLOW + "[*] Подключение к Discord..." + COLOR_RESET);
            
            jda = JDABuilder.createDefault(token)
                    .enableIntents(
                        GatewayIntent.GUILD_MEMBERS, 
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_WEBHOOKS
                    )
                    .build();
            
            jda.awaitReady();
            
            System.out.println(COLOR_GREEN + "[✓] Успешное подключение!" + COLOR_RESET);
            System.out.println(COLOR_GREEN + "[✓] Бот: " + jda.getSelfUser().getName() + COLOR_RESET);
            System.out.println();
            return true;
            
        } catch (InvalidTokenException e) {
            System.out.println(COLOR_RED + "[✗] Ошибка: Неверный токен!" + COLOR_RESET);
            return false;
        } catch (Exception e) {
            System.out.println(COLOR_RED + "[✗] Ошибка подключения: " + e.getMessage() + COLOR_RESET);
            return false;
        }
    }

    private static void selectServer() {
        System.out.println(COLOR_RED + "╔════════════════════════════════════╗");
        System.out.println("║     ВЫБОР СЕРВЕРА                  ║");
        System.out.println("╚════════════════════════════════════╝" + COLOR_RESET);
        System.out.println();
        
        System.out.print(COLOR_YELLOW + "[>] Введите ID сервера: " + COLOR_RESET);
        String guildId = scanner.nextLine().trim();
        
        selectedGuild = jda.getGuildById(guildId);
        
        if (selectedGuild != null) {
            System.out.println(COLOR_GREEN + "[✓] Сервер найден: " + selectedGuild.getName() + COLOR_RESET);
            System.out.println(COLOR_GREEN + "[✓] Участников: " + selectedGuild.getMemberCount() + COLOR_RESET);
            System.out.println();
        } else {
            System.out.println(COLOR_RED + "[✗] Сервер не найден!" + COLOR_RESET);
            System.out.println();
            selectServer();
        }
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println(COLOR_RED + "╔════════════════════════════════════╗");
            System.out.println("║     ГЛАВНОЕ МЕНЮ                   ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ [1] Управление участниками         ║");
            System.out.println("║ [2] Управление каналами            ║");
            System.out.println("║ [3] Управление ролями              ║");
            System.out.println("║ [4] Спам сообщениями               ║");
            System.out.println("║ [5] Управление пользователем       ║");
            System.out.println("║ [6] Управление сервером            ║");
            System.out.println("║ [7] PROXY АТАКА (все ПК)           ║");
            System.out.println("║ [8] Сменить сервер                 ║");
            System.out.println("║ [9] Выход                          ║");
            System.out.println("╚════════════════════════════════════╝" + COLOR_RESET);
            System.out.println();
            System.out.print(COLOR_YELLOW + "[>] Выберите опцию: " + COLOR_RESET);
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    memberMenu();
                    break;
                case "2":
                    channelMenu();
                    break;
                case "3":
                    roleMenu();
                    break;
                case "4":
                    spamMenu();
                    break;
                case "5":
                    userManagementMenu();
                    break;
                case "6":
                    serverMenu();
                    break;
                case "7":
                    ProxyManager.executeProxyAttack(jda.getToken(), selectedGuild.getId());
                    break;
                case "8":
                    selectServer();
                    break;
                case "9":
                    System.out.println(COLOR_RED + "[!] Выход из программы..." + COLOR_RESET);
                    if (jda != null) {
                        jda.shutdown();
                    }
                    return;
                default:
                    System.out.println(COLOR_RED + "[✗] Неверная опция!" + COLOR_RESET);
                    System.out.println();
            }
        }
    }

    private static void memberMenu() {
        System.out.println();
        System.out.println(COLOR_RED + "╔════════════════════════════════════╗");
        System.out.println("║   УПРАВЛЕНИЕ УЧАСТНИКАМИ           ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ [1] Кикнуть участника              ║");
        System.out.println("║ [2] Кикнуть всех участников        ║");
        System.out.println("║ [3] Забанить участника             ║");
        System.out.println("║ [4] Забанить всех участников       ║");
        System.out.println("║ [5] Мут всех участников            ║");
        System.out.println("║ [6] Изменить никнеймы всем         ║");
        System.out.println("║ [7] Таймаут всем участникам        ║");
        System.out.println("║ [8] ЛС всем участникам             ║");
        System.out.println("║ [9] Назад                          ║");
        System.out.println("╚════════════════════════════════════╝" + COLOR_RESET);
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Выберите опцию: " + COLOR_RESET);
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                kickMember();
                break;
            case "2":
                kickAllMembers();
                break;
            case "3":
                banMember();
                break;
            case "4":
                banAllMembers();
                break;
            case "5":
                muteAllMembers();
                break;
            case "6":
                changeAllNicknames();
                break;
            case "7":
                timeoutAllMembers();
                break;
            case "8":
                dmAllMembers();
                break;
            case "9":
                return;
            default:
                System.out.println(COLOR_RED + "[✗] Неверная опция!" + COLOR_RESET);
        }
    }

    private static void channelMenu() {
        System.out.println();
        System.out.println(COLOR_RED + "╔════════════════════════════════════╗");
        System.out.println("║   УПРАВЛЕНИЕ КАНАЛАМИ              ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ [1] Создать текстовые каналы       ║");
        System.out.println("║ [2] Удалить все каналы             ║");
        System.out.println("║ [3] Массовое переименование        ║");
        System.out.println("║ [4] Создать категорию              ║");
        System.out.println("║ [5] Удалить все категории          ║");
        System.out.println("║ [6] Создать голосовые каналы       ║");
        System.out.println("║ [7] Удалить голосовые каналы       ║");
        System.out.println("║ [8] Кикнуть из голосовых           ║");
        System.out.println("║ [9] Переместить всех в канал       ║");
        System.out.println("║ [10] Назад                         ║");
        System.out.println("╚════════════════════════════════════╝" + COLOR_RESET);
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Выберите опцию: " + COLOR_RESET);
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                createChannels();
                break;
            case "2":
                deleteAllChannels();
                break;
            case "3":
                massRenameChannels();
                break;
            case "4":
                createCategory();
                break;
            case "5":
                deleteAllCategories();
                break;
            case "6":
                createVoiceChannels();
                break;
            case "7":
                deleteAllVoiceChannels();
                break;
            case "8":
                kickFromVoice();
                break;
            case "9":
                moveAllToChannel();
                break;
            case "10":
                return;
            default:
                System.out.println(COLOR_RED + "[✗] Неверная опция!" + COLOR_RESET);
        }
    }

    private static void roleMenu() {
        System.out.println();
        System.out.println(COLOR_RED + "╔════════════════════════════════════╗");
        System.out.println("║   УПРАВЛЕНИЕ РОЛЯМИ                ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ [1] Создать роли                   ║");
        System.out.println("║ [2] Выдать роль всем               ║");
        System.out.println("║ [3] Удалить все роли               ║");
        System.out.println("║ [4] Назад                          ║");
        System.out.println("╚════════════════════════════════════╝" + COLOR_RESET);
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Выберите опцию: " + COLOR_RESET);
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                createRoles();
                break;
            case "2":
                giveRoleToAll();
                break;
            case "3":
                deleteAllRoles();
                break;
            case "4":
                return;
            default:
                System.out.println(COLOR_RED + "[✗] Неверная опция!" + COLOR_RESET);
        }
    }

    private static void spamMenu() {
        System.out.println();
        System.out.println(COLOR_RED + "╔════════════════════════════════════╗");
        System.out.println("║   СПАМ СООБЩЕНИЯМИ                 ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ [1] Спам через вебхуки             ║");
        System.out.println("║ [2] Спам от бота                   ║");
        System.out.println("║ [3] Спам везде (вебхуки + бот)     ║");
        System.out.println("║ [4] Спам с эмбедами                ║");
        System.out.println("║ [5] Спам файлами/картинками        ║");
        System.out.println("║ [6] Массовый спам во все каналы    ║");
        System.out.println("║ [7] Назад                          ║");
        System.out.println("╚════════════════════════════════════╝" + COLOR_RESET);
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Выберите опцию: " + COLOR_RESET);
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                spamWebhooks();
                break;
            case "2":
                spamBot();
                break;
            case "3":
                spamEverywhere();
                break;
            case "4":
                spamEmbeds();
                break;
            case "5":
                spamFiles();
                break;
            case "6":
                massSpamAllChannels();
                break;
            case "7":
                return;
            default:
                System.out.println(COLOR_RED + "[✗] Неверная опция!" + COLOR_RESET);
        }
    }

    private static void userManagementMenu() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Введите ID пользователя: " + COLOR_RESET);
        String userId = scanner.nextLine().trim();
        
        selectedGuild.retrieveMemberById(userId).queue(member -> {
            System.out.println(COLOR_GREEN + "[✓] Пользователь: " + member.getUser().getName() + COLOR_RESET);
            System.out.println();
            
            boolean managing = true;
            while (managing) {
                System.out.println(COLOR_RED + "╔════════════════════════════════════╗");
                System.out.println("║   УПРАВЛЕНИЕ ПОЛЬЗОВАТЕЛЕМ         ║");
                System.out.println("╠════════════════════════════════════╣");
                System.out.println("║ [1] Выдать роль                    ║");
                System.out.println("║ [2] Забрать роль                   ║");
                System.out.println("║ [3] Показать роли                  ║");
                System.out.println("║ [4] Кикнуть пользователя           ║");
                System.out.println("║ [5] Забрать все роли               ║");
                System.out.println("║ [6] Показать информацию            ║");
                System.out.println("║ [7] Назад                          ║");
                System.out.println("╚════════════════════════════════════╝" + COLOR_RESET);
                System.out.println();
                System.out.print(COLOR_YELLOW + "[>] Выберите опцию: " + COLOR_RESET);
                
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        addRoleToUser(member);
                        break;
                    case "2":
                        removeRoleFromUser(member);
                        break;
                    case "3":
                        showUserRoles(member);
                        break;
                    case "4":
                        kickUser(member);
                        managing = false;
                        break;
                    case "5":
                        removeAllRolesFromUser(member);
                        break;
                    case "6":
                        showUserInfo(member);
                        break;
                    case "7":
                        managing = false;
                        break;
                    default:
                        System.out.println(COLOR_RED + "[✗] Неверная опция!" + COLOR_RESET);
                }
            }
        }, error -> {
            System.out.println(COLOR_RED + "[✗] Пользователь не найден!" + COLOR_RESET);
        });
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void createChannels() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Название канала: " + COLOR_RESET);
        String channelName = scanner.nextLine().trim();
        
        System.out.print(COLOR_YELLOW + "[>] Количество каналов: " + COLOR_RESET);
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(COLOR_RED + "[✗] Неверное число!" + COLOR_RESET);
            return;
        }
        
        System.out.println(COLOR_YELLOW + "[*] Создание каналов..." + COLOR_RESET);
        
        for (int i = 0; i < count; i++) {
            final int index = i;
            selectedGuild.createTextChannel(channelName + "-" + i).queue(
                success -> System.out.println(COLOR_GREEN + "[✓] Создан канал: " + channelName + "-" + index + COLOR_RESET),
                error -> System.out.println(COLOR_RED + "[✗] Ошибка создания канала " + index + COLOR_RESET)
            );
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void deleteAllChannels() {
        System.out.println();
        System.out.print(COLOR_RED + "[!] Удалить ВСЕ каналы? (yes/no): " + COLOR_RESET);
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("yes")) {
            System.out.println(COLOR_YELLOW + "[!] Операция отменена" + COLOR_RESET);
            return;
        }
        
        List<TextChannel> channels = selectedGuild.getTextChannels();
        System.out.println(COLOR_YELLOW + "[*] Удаление каналов..." + COLOR_RESET);
        
        for (TextChannel channel : channels) {
            channel.delete().queue(
                success -> System.out.println(COLOR_GREEN + "[✓] Удален: " + channel.getName() + COLOR_RESET),
                error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + channel.getName() + COLOR_RESET)
            );
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void createRoles() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Название роли: " + COLOR_RESET);
        String roleName = scanner.nextLine().trim();
        
        System.out.print(COLOR_YELLOW + "[>] Количество ролей: " + COLOR_RESET);
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(COLOR_RED + "[✗] Неверное число!" + COLOR_RESET);
            return;
        }
        
        System.out.print(COLOR_YELLOW + "[>] Цвет роли (HEX, например FF0000 для красного): " + COLOR_RESET);
        String colorHex = scanner.nextLine().trim();
        Color roleColor;
        
        try {
            roleColor = Color.decode("#" + colorHex);
        } catch (Exception e) {
            roleColor = Color.RED;
            System.out.println(COLOR_YELLOW + "[!] Неверный цвет, используется красный" + COLOR_RESET);
        }
        
        System.out.println(COLOR_YELLOW + "[*] Создание ролей..." + COLOR_RESET);
        
        for (int i = 0; i < count; i++) {
            final int index = i;
            selectedGuild.createRole()
                .setName(roleName + "-" + i)
                .setColor(roleColor)
                .queue(
                    success -> System.out.println(COLOR_GREEN + "[✓] Создана роль: " + roleName + "-" + index + COLOR_RESET),
                    error -> System.out.println(COLOR_RED + "[✗] Ошибка создания роли " + index + COLOR_RESET)
                );
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void giveRoleToAll() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] ID роли для выдачи: " + COLOR_RESET);
        String roleId = scanner.nextLine().trim();
        
        Role role = selectedGuild.getRoleById(roleId);
        if (role == null) {
            System.out.println(COLOR_RED + "[✗] Роль не найдена!" + COLOR_RESET);
            return;
        }
        
        System.out.println(COLOR_YELLOW + "[*] Выдача роли всем участникам..." + COLOR_RESET);
        
        selectedGuild.loadMembers().onSuccess(members -> {
            for (Member member : members) {
                if (member.getUser().isBot()) continue;
                
                selectedGuild.addRoleToMember(member, role).queue(
                    success -> System.out.println(COLOR_GREEN + "[✓] Роль выдана: " + member.getUser().getName() + COLOR_RESET),
                    error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + member.getUser().getName() + COLOR_RESET)
                );
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        });
    }

    private static void deleteAllRoles() {
        System.out.println();
        System.out.print(COLOR_RED + "[!] Удалить все роли? (yes/no): " + COLOR_RESET);
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("yes")) {
            System.out.println(COLOR_YELLOW + "[!] Операция отменена" + COLOR_RESET);
            return;
        }
        
        List<Role> roles = selectedGuild.getRoles();
        System.out.println(COLOR_YELLOW + "[*] Удаление ролей..." + COLOR_RESET);
        
        for (Role role : roles) {
            if (role.isManaged() || role.isPublicRole()) continue;
            
            role.delete().queue(
                success -> System.out.println(COLOR_GREEN + "[✓] Удалена: " + role.getName() + COLOR_RESET),
                error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + role.getName() + COLOR_RESET)
            );
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void spamWebhooks() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Сообщение для спама: " + COLOR_RESET);
        String message = scanner.nextLine().trim();
        
        System.out.print(COLOR_YELLOW + "[>] Количество сообщений: " + COLOR_RESET);
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(COLOR_RED + "[✗] Неверное число!" + COLOR_RESET);
            return;
        }
        
        List<TextChannel> channels = selectedGuild.getTextChannels();
        System.out.println(COLOR_YELLOW + "[*] Создание вебхуков и спам..." + COLOR_RESET);
        
        for (TextChannel channel : channels) {
            channel.createWebhook("SpamWebhook").queue(webhook -> {
                System.out.println(COLOR_GREEN + "[✓] Вебхук создан в: " + channel.getName() + COLOR_RESET);
            }, error -> {
                System.out.println(COLOR_RED + "[✗] Ошибка создания вебхука в: " + channel.getName() + COLOR_RESET);
            });
        }
        
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        
        for (int i = 0; i < count; i++) {
            for (TextChannel channel : channels) {
                channel.retrieveWebhooks().queue(webhooks -> {
                    if (!webhooks.isEmpty()) {
                        webhooks.get(0).sendMessage(message).queue();
                    }
                });
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
        
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void spamBot() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Сообщение для спама: " + COLOR_RESET);
        String message = scanner.nextLine().trim();
        
        System.out.print(COLOR_YELLOW + "[>] Количество сообщений: " + COLOR_RESET);
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(COLOR_RED + "[✗] Неверное число!" + COLOR_RESET);
            return;
        }
        
        List<TextChannel> channels = selectedGuild.getTextChannels();
        System.out.println(COLOR_YELLOW + "[*] Спам от бота..." + COLOR_RESET);
        
        for (int i = 0; i < count; i++) {
            for (TextChannel channel : channels) {
                channel.sendMessage(message).queue();
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
        
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void spamEverywhere() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Сообщение для спама: " + COLOR_RESET);
        String message = scanner.nextLine().trim();
        
        System.out.print(COLOR_YELLOW + "[>] Количество сообщений: " + COLOR_RESET);
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(COLOR_RED + "[✗] Неверное число!" + COLOR_RESET);
            return;
        }
        
        List<TextChannel> channels = selectedGuild.getTextChannels();
        System.out.println(COLOR_YELLOW + "[*] Спам везде (вебхуки + бот)..." + COLOR_RESET);
        
        for (TextChannel channel : channels) {
            channel.createWebhook("SpamWebhook").queue(webhook -> {
                System.out.println(COLOR_GREEN + "[✓] Вебхук создан в: " + channel.getName() + COLOR_RESET);
            });
        }
        
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        
        for (int i = 0; i < count; i++) {
            for (TextChannel channel : channels) {
                if (i % 2 == 0) {
                    channel.retrieveWebhooks().queue(webhooks -> {
                        if (!webhooks.isEmpty()) {
                            webhooks.get(0).sendMessage(message).queue();
                        }
                    });
                } else {
                    channel.sendMessage(message).queue();
                }
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
        
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void addRoleToUser(Member member) {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] ID роли для выдачи: " + COLOR_RESET);
        String roleId = scanner.nextLine().trim();
        
        Role role = selectedGuild.getRoleById(roleId);
        if (role == null) {
            System.out.println(COLOR_RED + "[✗] Роль не найдена!" + COLOR_RESET);
            return;
        }
        
        selectedGuild.addRoleToMember(member, role).queue(
            success -> System.out.println(COLOR_GREEN + "[✓] Роль выдана!" + COLOR_RESET),
            error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + error.getMessage() + COLOR_RESET)
        );
    }

    private static void removeRoleFromUser(Member member) {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] ID роли для удаления: " + COLOR_RESET);
        String roleId = scanner.nextLine().trim();
        
        Role role = selectedGuild.getRoleById(roleId);
        if (role == null) {
            System.out.println(COLOR_RED + "[✗] Роль не найдена!" + COLOR_RESET);
            return;
        }
        
        selectedGuild.removeRoleFromMember(member, role).queue(
            success -> System.out.println(COLOR_GREEN + "[✓] Роль удалена!" + COLOR_RESET),
            error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + error.getMessage() + COLOR_RESET)
        );
    }

    private static void showUserRoles(Member member) {
        System.out.println();
        System.out.println(COLOR_GREEN + "[*] Роли пользователя " + member.getUser().getName() + ":" + COLOR_RESET);
        
        for (Role role : member.getRoles()) {
            System.out.println(COLOR_YELLOW + "  - " + role.getName() + " (ID: " + role.getId() + ")" + COLOR_RESET);
        }
        System.out.println();
    }

    private static void kickUser(Member member) {
        member.kick().queue(
            success -> System.out.println(COLOR_GREEN + "[✓] Пользователь кикнут!" + COLOR_RESET),
            error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + error.getMessage() + COLOR_RESET)
        );
    }

    private static void removeAllRolesFromUser(Member member) {
        System.out.println(COLOR_YELLOW + "[*] Удаление всех ролей..." + COLOR_RESET);
        
        List<Role> roles = new ArrayList<>(member.getRoles());
        for (Role role : roles) {
            selectedGuild.removeRoleFromMember(member, role).queue(
                success -> System.out.println(COLOR_GREEN + "[✓] Роль удалена: " + role.getName() + COLOR_RESET),
                error -> {}
            );
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(COLOR_GREEN + "[✓] Все роли удалены!" + COLOR_RESET);
    }

    private static void kickMember() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Введите ID участника для кика: " + COLOR_RESET);
        String memberId = scanner.nextLine().trim();
        
        selectedGuild.retrieveMemberById(memberId).queue(member -> {
            member.kick().queue(
                success -> {
                    System.out.println(COLOR_GREEN + "[✓] Участник " + member.getUser().getName() + " был кикнут!" + COLOR_RESET);
                    System.out.println();
                },
                error -> {
                    System.out.println(COLOR_RED + "[✗] Ошибка: " + error.getMessage() + COLOR_RESET);
                    System.out.println();
                }
            );
        }, error -> {
            System.out.println(COLOR_RED + "[✗] Участник не найден!" + COLOR_RESET);
            System.out.println();
        });
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void kickAllMembers() {
        System.out.println();
        System.out.print(COLOR_RED + "[!] Вы уверены? Это кикнет ВСЕХ участников! (yes/no): " + COLOR_RESET);
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("yes")) {
            System.out.println(COLOR_YELLOW + "[!] Операция отменена" + COLOR_RESET);
            System.out.println();
            return;
        }
        
        selectedGuild.loadMembers().onSuccess(members -> {
            int kicked = 0;
            int failed = 0;
            
            System.out.println(COLOR_YELLOW + "[*] Начинается массовый кик..." + COLOR_RESET);
            
            for (Member member : members) {
                if (member.getUser().isBot() || member.isOwner()) {
                    continue;
                }
                
                try {
                    member.kick().queue(
                        success -> System.out.println(COLOR_GREEN + "[✓] Кикнут: " + member.getUser().getName() + COLOR_RESET),
                        error -> System.out.println(COLOR_RED + "[✗] Ошибка с: " + member.getUser().getName() + COLOR_RESET)
                    );
                    kicked++;
                    Thread.sleep(1000);
                } catch (Exception e) {
                    failed++;
                }
            }
            
            System.out.println();
            System.out.println(COLOR_GREEN + "[✓] Завершено! Кикнуто: " + kicked + COLOR_RESET);
            if (failed > 0) {
                System.out.println(COLOR_RED + "[!] Ошибок: " + failed + COLOR_RESET);
            }
            System.out.println();
        });
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void serverMenu() {
        System.out.println();
        System.out.println(COLOR_RED + "╔════════════════════════════════════╗");
        System.out.println("║   УПРАВЛЕНИЕ СЕРВЕРОМ              ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ [1] Изменить название сервера      ║");
        System.out.println("║ [2] Изменить иконку сервера        ║");
        System.out.println("║ [3] Изменить описание сервера      ║");
        System.out.println("║ [4] Назад                          ║");
        System.out.println("╚════════════════════════════════════╝" + COLOR_RESET);
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Выберите опцию: " + COLOR_RESET);
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                changeServerName();
                break;
            case "2":
                changeServerIcon();
                break;
            case "3":
                changeServerDescription();
                break;
            case "4":
                return;
            default:
                System.out.println(COLOR_RED + "[✗] Неверная опция!" + COLOR_RESET);
        }
    }

    private static void changeServerName() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Новое название сервера: " + COLOR_RESET);
        String newName = scanner.nextLine().trim();
        
        selectedGuild.getManager().setName(newName).queue(
            success -> System.out.println(COLOR_GREEN + "[✓] Название изменено!" + COLOR_RESET),
            error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + error.getMessage() + COLOR_RESET)
        );
        System.out.println();
    }

    private static void changeServerIcon() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] URL иконки: " + COLOR_RESET);
        String iconUrl = scanner.nextLine().trim();
        
        try {
            Icon icon = Icon.from(new URL(iconUrl).openStream());
            selectedGuild.getManager().setIcon(icon).queue(
                success -> System.out.println(COLOR_GREEN + "[✓] Иконка изменена!" + COLOR_RESET),
                error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + error.getMessage() + COLOR_RESET)
            );
        } catch (Exception e) {
            System.out.println(COLOR_RED + "[✗] Ошибка загрузки иконки: " + e.getMessage() + COLOR_RESET);
        }
        System.out.println();
    }

    private static void changeServerDescription() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Новое описание сервера: " + COLOR_RESET);
        String newDescription = scanner.nextLine().trim();
        
        selectedGuild.getManager().setDescription(newDescription).queue(
            success -> System.out.println(COLOR_GREEN + "[✓] Описание изменено!" + COLOR_RESET),
            error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + error.getMessage() + COLOR_RESET)
        );
        System.out.println();
    }

    private static void massRenameChannels() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Новое название для каналов: " + COLOR_RESET);
        String newName = scanner.nextLine().trim();
        
        List<TextChannel> channels = selectedGuild.getTextChannels();
        System.out.println(COLOR_YELLOW + "[*] Переименование каналов..." + COLOR_RESET);
        
        for (int i = 0; i < channels.size(); i++) {
            final int index = i;
            TextChannel channel = channels.get(i);
            channel.getManager().setName(newName + "-" + i).queue(
                success -> System.out.println(COLOR_GREEN + "[✓] Переименован: " + newName + "-" + index + COLOR_RESET),
                error -> System.out.println(COLOR_RED + "[✗] Ошибка канала " + index + COLOR_RESET)
            );
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void createCategory() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Название категории: " + COLOR_RESET);
        String categoryName = scanner.nextLine().trim();
        
        selectedGuild.createCategory(categoryName).queue(
            success -> System.out.println(COLOR_GREEN + "[✓] Категория создана: " + categoryName + COLOR_RESET),
            error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + error.getMessage() + COLOR_RESET)
        );
        System.out.println();
    }

    private static void deleteAllCategories() {
        System.out.println();
        System.out.print(COLOR_RED + "[!] Удалить все категории? (yes/no): " + COLOR_RESET);
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("yes")) {
            System.out.println(COLOR_YELLOW + "[!] Операция отменена" + COLOR_RESET);
            return;
        }
        
        List<Category> categories = selectedGuild.getCategories();
        System.out.println(COLOR_YELLOW + "[*] Удаление категорий..." + COLOR_RESET);
        
        for (Category category : categories) {
            category.delete().queue(
                success -> System.out.println(COLOR_GREEN + "[✓] Удалена: " + category.getName() + COLOR_RESET),
                error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + category.getName() + COLOR_RESET)
            );
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void banMember() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] ID участника для бана: " + COLOR_RESET);
        String memberId = scanner.nextLine().trim();
        
        selectedGuild.ban(UserSnowflake.fromId(memberId), 0, TimeUnit.DAYS).queue(
            success -> System.out.println(COLOR_GREEN + "[✓] Участник забанен!" + COLOR_RESET),
            error -> System.out.println(COLOR_RED + "[✗] Ошибка: " + error.getMessage() + COLOR_RESET)
        );
        System.out.println();
    }

    private static void banAllMembers() {
        System.out.println();
        System.out.print(COLOR_RED + "[!] Забанить ВСЕХ? (yes/no): " + COLOR_RESET);
        if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) return;
        
        selectedGuild.loadMembers().onSuccess(members -> {
            System.out.println(COLOR_YELLOW + "[*] Бан участников..." + COLOR_RESET);
            for (Member member : members) {
                if (member.getUser().isBot() || member.isOwner()) continue;
                member.ban(0, TimeUnit.DAYS).queue(
                    s -> System.out.println(COLOR_GREEN + "[✓] Забанен: " + member.getUser().getName() + COLOR_RESET),
                    e -> {}
                );
                try { Thread.sleep(1000); } catch (InterruptedException ex) {}
            }
        });
    }

    private static void muteAllMembers() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Длительность мута (минуты): " + COLOR_RESET);
        int minutes = Integer.parseInt(scanner.nextLine().trim());
        
        selectedGuild.loadMembers().onSuccess(members -> {
            System.out.println(COLOR_YELLOW + "[*] Мут участников..." + COLOR_RESET);
            for (Member member : members) {
                if (member.getUser().isBot()) continue;
                member.timeoutFor(Duration.ofMinutes(minutes)).queue(
                    s -> System.out.println(COLOR_GREEN + "[✓] Мут: " + member.getUser().getName() + COLOR_RESET),
                    e -> {}
                );
                try { Thread.sleep(1000); } catch (InterruptedException ex) {}
            }
        });
    }

    private static void changeAllNicknames() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Новый никнейм: " + COLOR_RESET);
        String nickname = scanner.nextLine().trim();
        
        selectedGuild.loadMembers().onSuccess(members -> {
            System.out.println(COLOR_YELLOW + "[*] Изменение никнеймов..." + COLOR_RESET);
            for (Member member : members) {
                if (member.getUser().isBot() || member.isOwner()) continue;
                if (!selectedGuild.getSelfMember().canInteract(member)) continue;
                member.modifyNickname(nickname).queue(
                    s -> System.out.println(COLOR_GREEN + "[✓] Изменен: " + member.getUser().getName() + COLOR_RESET),
                    e -> {}
                );
                try { Thread.sleep(1000); } catch (InterruptedException ex) {}
            }
        });
    }

    private static void timeoutAllMembers() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Длительность таймаута (минуты): " + COLOR_RESET);
        int minutes = Integer.parseInt(scanner.nextLine().trim());
        
        selectedGuild.loadMembers().onSuccess(members -> {
            System.out.println(COLOR_YELLOW + "[*] Таймаут участникам..." + COLOR_RESET);
            for (Member member : members) {
                if (member.getUser().isBot()) continue;
                member.timeoutFor(Duration.ofMinutes(minutes)).queue(
                    s -> System.out.println(COLOR_GREEN + "[✓] Таймаут: " + member.getUser().getName() + COLOR_RESET),
                    e -> {}
                );
                try { Thread.sleep(1000); } catch (InterruptedException ex) {}
            }
        });
    }

    private static void dmAllMembers() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Сообщение для ЛС: " + COLOR_RESET);
        String message = scanner.nextLine().trim();
        
        selectedGuild.loadMembers().onSuccess(members -> {
            System.out.println(COLOR_YELLOW + "[*] Отправка ЛС..." + COLOR_RESET);
            for (Member member : members) {
                if (member.getUser().isBot()) continue;
                member.getUser().openPrivateChannel().queue(channel -> {
                    channel.sendMessage(message).queue(
                        s -> System.out.println(COLOR_GREEN + "[✓] Отправлено: " + member.getUser().getName() + COLOR_RESET),
                        e -> System.out.println(COLOR_RED + "[✗] Ошибка: " + member.getUser().getName() + COLOR_RESET)
                    );
                });
                try { Thread.sleep(2000); } catch (InterruptedException ex) {}
            }
        });
    }

    private static void showUserInfo(Member member) {
        System.out.println();
        System.out.println(COLOR_GREEN + "╔════════════════════════════════════╗");
        System.out.println("║   ИНФОРМАЦИЯ О ПОЛЬЗОВАТЕЛЕ        ║");
        System.out.println("╚════════════════════════════════════╝" + COLOR_RESET);
        System.out.println(COLOR_YELLOW + "Имя: " + member.getUser().getName() + COLOR_RESET);
        System.out.println(COLOR_YELLOW + "ID: " + member.getId() + COLOR_RESET);
        System.out.println(COLOR_YELLOW + "Дата регистрации: " + member.getUser().getTimeCreated() + COLOR_RESET);
        System.out.println(COLOR_YELLOW + "Дата присоединения: " + member.getTimeJoined() + COLOR_RESET);
        System.out.println(COLOR_YELLOW + "Роли: " + member.getRoles().size() + COLOR_RESET);
        for (Role role : member.getRoles()) {
            System.out.println(COLOR_YELLOW + "  - " + role.getName() + COLOR_RESET);
        }
        System.out.println();
    }

    private static void createVoiceChannels() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Название канала: " + COLOR_RESET);
        String name = scanner.nextLine().trim();
        System.out.print(COLOR_YELLOW + "[>] Количество: " + COLOR_RESET);
        int count = Integer.parseInt(scanner.nextLine().trim());
        
        System.out.println(COLOR_YELLOW + "[*] Создание голосовых каналов..." + COLOR_RESET);
        for (int i = 0; i < count; i++) {
            final int index = i;
            selectedGuild.createVoiceChannel(name + "-" + i).queue(
                s -> System.out.println(COLOR_GREEN + "[✓] Создан: " + name + "-" + index + COLOR_RESET),
                e -> {}
            );
            try { Thread.sleep(500); } catch (InterruptedException ex) {}
        }
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void deleteAllVoiceChannels() {
        System.out.println();
        System.out.print(COLOR_RED + "[!] Удалить все голосовые? (yes/no): " + COLOR_RESET);
        if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) return;
        
        System.out.println(COLOR_YELLOW + "[*] Удаление голосовых каналов..." + COLOR_RESET);
        for (VoiceChannel channel : selectedGuild.getVoiceChannels()) {
            channel.delete().queue(
                s -> System.out.println(COLOR_GREEN + "[✓] Удален: " + channel.getName() + COLOR_RESET),
                e -> {}
            );
            try { Thread.sleep(500); } catch (InterruptedException ex) {}
        }
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void kickFromVoice() {
        System.out.println(COLOR_YELLOW + "[*] Кик из голосовых каналов..." + COLOR_RESET);
        selectedGuild.loadMembers().onSuccess(members -> {
            for (Member member : members) {
                if (member.getVoiceState() != null && member.getVoiceState().inAudioChannel()) {
                    selectedGuild.kickVoiceMember(member).queue(
                        s -> System.out.println(COLOR_GREEN + "[✓] Кикнут: " + member.getUser().getName() + COLOR_RESET),
                        e -> {}
                    );
                }
            }
        });
        System.out.println();
    }

    private static void moveAllToChannel() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] ID голосового канала: " + COLOR_RESET);
        String channelId = scanner.nextLine().trim();
        
        VoiceChannel targetChannel = selectedGuild.getVoiceChannelById(channelId);
        if (targetChannel == null) {
            System.out.println(COLOR_RED + "[✗] Канал не найден!" + COLOR_RESET);
            return;
        }
        
        System.out.println(COLOR_YELLOW + "[*] Перемещение участников..." + COLOR_RESET);
        selectedGuild.loadMembers().onSuccess(members -> {
            for (Member member : members) {
                if (member.getVoiceState() != null && member.getVoiceState().inAudioChannel()) {
                    selectedGuild.moveVoiceMember(member, targetChannel).queue(
                        s -> System.out.println(COLOR_GREEN + "[✓] Перемещен: " + member.getUser().getName() + COLOR_RESET),
                        e -> {}
                    );
                }
            }
        });
        System.out.println();
    }

    private static void spamEmbeds() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Заголовок эмбеда: " + COLOR_RESET);
        String title = scanner.nextLine().trim();
        System.out.print(COLOR_YELLOW + "[>] Описание: " + COLOR_RESET);
        String description = scanner.nextLine().trim();
        System.out.print(COLOR_YELLOW + "[>] URL картинки: " + COLOR_RESET);
        String imageUrl = scanner.nextLine().trim();
        System.out.print(COLOR_YELLOW + "[>] Количество сообщений: " + COLOR_RESET);
        int count = Integer.parseInt(scanner.nextLine().trim());
        
        EmbedBuilder embed = new EmbedBuilder()
            .setTitle(title)
            .setDescription(description)
            .setImage(imageUrl)
            .setColor(java.awt.Color.RED);
        
        System.out.println(COLOR_YELLOW + "[*] Спам эмбедами..." + COLOR_RESET);
        for (int i = 0; i < count; i++) {
            for (TextChannel channel : selectedGuild.getTextChannels()) {
                channel.sendMessageEmbeds(embed.build()).queue();
            }
            try { Thread.sleep(1000); } catch (InterruptedException ex) {}
        }
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void spamFiles() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Путь к файлу: " + COLOR_RESET);
        String filePath = scanner.nextLine().trim();
        System.out.print(COLOR_YELLOW + "[>] Количество сообщений: " + COLOR_RESET);
        int count = Integer.parseInt(scanner.nextLine().trim());
        
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println(COLOR_RED + "[✗] Файл не найден!" + COLOR_RESET);
            return;
        }
        
        System.out.println(COLOR_YELLOW + "[*] Спам файлами..." + COLOR_RESET);
        for (int i = 0; i < count; i++) {
            for (TextChannel channel : selectedGuild.getTextChannels()) {
                channel.sendFiles(FileUpload.fromData(file)).queue();
            }
            try { Thread.sleep(1000); } catch (InterruptedException ex) {}
        }
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }

    private static void massSpamAllChannels() {
        System.out.println();
        System.out.print(COLOR_YELLOW + "[>] Сообщение: " + COLOR_RESET);
        String message = scanner.nextLine().trim();
        System.out.print(COLOR_YELLOW + "[>] Количество сообщений: " + COLOR_RESET);
        int count = Integer.parseInt(scanner.nextLine().trim());
        System.out.print(COLOR_YELLOW + "[>] Использовать вебхуки? (yes/no): " + COLOR_RESET);
        boolean useWebhooks = scanner.nextLine().trim().equalsIgnoreCase("yes");
        
        System.out.println(COLOR_YELLOW + "[*] Массовый спам..." + COLOR_RESET);
        
        if (useWebhooks) {
            for (TextChannel channel : selectedGuild.getTextChannels()) {
                channel.createWebhook("Spam").queue(webhook -> {
                    System.out.println(COLOR_GREEN + "[✓] Вебхук создан в: " + channel.getName() + COLOR_RESET);
                });
            }
            try { Thread.sleep(2000); } catch (InterruptedException ex) {}
            
            for (int i = 0; i < count; i++) {
                for (TextChannel channel : selectedGuild.getTextChannels()) {
                    channel.retrieveWebhooks().queue(webhooks -> {
                        if (!webhooks.isEmpty()) {
                            webhooks.get(0).sendMessage(message).queue();
                        }
                    });
                }
                try { Thread.sleep(1000); } catch (InterruptedException ex) {}
            }
        } else {
            for (int i = 0; i < count; i++) {
                for (TextChannel channel : selectedGuild.getTextChannels()) {
                    channel.sendMessage(message).queue();
                }
                try { Thread.sleep(1000); } catch (InterruptedException ex) {}
            }
        }
        
        System.out.println(COLOR_GREEN + "[✓] Завершено!" + COLOR_RESET);
        System.out.println();
    }
}