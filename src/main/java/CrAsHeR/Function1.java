/*
 *  |CrAsHeR CrAsHeR CrAsHeR CrAsHeR CrAsHeR CrAsHeR|
 * 
 *  Данная программа написана на языке Java. 
 *  Сама программа представляет собой мульти-тул, в котором есть всё 
 *  необходимое — от базовых команд для спама в Discord до краша серверов Minecraft и так далее. 
 *  Программа была написана одним человеком — nezi4k.
 *  Автор: nezi4k
 *  Версия: 1.0
 *  Дата создания: ꜱᴛᴀʀᴛᴇᴅ ꜰʀᴏᴍ 23.05.2025
 * 
 *  |CrAsHeR CrAsHeR CrAsHeR CrAsHeR CrAsHeR CrAsHeR|
 */

package CrAsHeR;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Function1 {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BRIGHT_RED = "\u001B[91m";
    private static final long MIN_DELAY_MS = 250;
    private static final int MAX_RETRIES = 3;
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .executor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
            .build();

    public static void execute() {
        Scanner scanner = new Scanner(System.in);
        List<String> webhookUrls = new ArrayList<>();
        String messageContent;
        int messageCount;
        long delayMs;

        try {
            System.out.println(ANSI_RED + "СПАМ ВЕБХУКАМИ ОПАСЕН ДЛЯ ВАШИХ АКАУНТОВ ВАС МОГУТ ЗАБЛОКИРОВАТЬ ЗА МНОГО ЧИСЛЕНЫЙ СПАМ");
            System.out.println(ANSI_RED + "Выберите способ ввода вебхуков: ");
            System.out.println(ANSI_BRIGHT_RED + "1. Ввести вручную");
            System.out.println(ANSI_BRIGHT_RED + "2. Загрузить из файла .txt");
            System.out.print(ANSI_BRIGHT_RED + "Введите номер (1 или 2): ");
            String inputChoice = scanner.nextLine();

            if ("2".equals(inputChoice)) {
                webhookUrls = loadWebhooksFromFile(scanner);
                if (webhookUrls.isEmpty()) {
                    System.out.println(ANSI_RED + "Ошибка: не удалось загрузить вебхуки из файла. Завершение программы.");
                    return;
                }
            } else {
                System.out.println(ANSI_RED + "Введите URL вебхуков Discord (введите 'готово' для завершения):");
                while (true) {
                    System.out.print(ANSI_BRIGHT_RED + "URL вебхука: ");
                    String url = scanner.nextLine();
                    if (url.equalsIgnoreCase("готово")) {
                        if (webhookUrls.isEmpty()) {
                            System.out.println(ANSI_RED + "Ошибка: нужен хотя бы один URL вебхука!");
                            continue;
                        }
                        break;
                    }
                    if (isValidUrl(url)) {
                        webhookUrls.add(url);
                    } else {
                        System.out.println(ANSI_RED + "Неверный URL! Введите корректный URL вебхука Discord.");
                    }
                }
            }

            System.out.print(ANSI_BRIGHT_RED + "Введите текст сообщения: ");
            messageContent = scanner.nextLine();

            while (true) {
                try {
                    System.out.print(ANSI_BRIGHT_RED + "Введите количество сообщений: ");
                    messageCount = Integer.parseInt(scanner.nextLine());
                    if (messageCount > 0) {
                        break;
                    } else {
                        System.out.println(ANSI_RED + "Введите положительное число!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(ANSI_RED + "Ошибка ввода! Введите число.");
                }
            }

            while (true) {
                try {
                    System.out.print(ANSI_BRIGHT_RED + "Введите задержку между сообщениями в миллисекундах (по умолчанию 250): ");
                    String input = scanner.nextLine();
                    delayMs = input.isEmpty() ? MIN_DELAY_MS : Long.parseLong(input);
                    if (delayMs < MIN_DELAY_MS) {
                        System.out.println(ANSI_RED + "Задержка увеличена до " + MIN_DELAY_MS + " мс для соблюдения лимитов Discord.");
                        delayMs = MIN_DELAY_MS;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ANSI_RED + "Ошибка ввода! Введите число или оставьте пустым для значения по умолчанию.");
                }
            }

            System.out.println(ANSI_RED + "Начинаю отправку сообщений...");
            sendMessagesConcurrently(webhookUrls, messageContent, messageCount, delayMs);

            System.out.println(ANSI_RED + "Отправлено " + (long) messageCount * webhookUrls.size() + " сообщений!");
        } finally {
            scanner.close();
        }
    }

    private static List<String> loadWebhooksFromFile(Scanner scanner) {
        List<String> webhookUrls = new ArrayList<>();
        System.out.print(ANSI_BRIGHT_RED + "Введите путь к файлу .txt с вебхуками: ");
        String filePath = scanner.nextLine();

        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path) || !Files.isRegularFile(path)) {
                System.out.println(ANSI_RED + "Ошибка: файл не существует или не является файлом!");
                return webhookUrls;
            }

            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty() && isValidUrl(trimmedLine)) {
                    webhookUrls.add(trimmedLine);
                } else if (!trimmedLine.isEmpty()) {
                    System.out.println(ANSI_RED + "Пропущен неверный URL: " + trimmedLine);
                }
            }

            if (webhookUrls.isEmpty()) {
                System.out.println(ANSI_RED + "Ошибка: в файле нет действительных URL вебхуков!");
            } else {
                System.out.println(ANSI_BRIGHT_RED + "Загружено " + webhookUrls.size() + " вебхуков из файла.");
            }
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Ошибка при чтении файла: " + e.getMessage());
        }

        return webhookUrls;
    }

    private static void sendMessagesConcurrently(List<String> webhookUrls, String content, int messageCount, long delayMs) {
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(webhookUrls.size(), Runtime.getRuntime().availableProcessors()));
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        for (String webhookUrl : webhookUrls) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                for (int i = 1; i <= messageCount; i++) {
                    int attempt = 0;
                    boolean success = false;
                    while (attempt < MAX_RETRIES && !success) {
                        try {
                            sendWebhookMessage(webhookUrl, content);
                            System.out.println(ANSI_BRIGHT_RED + "Сообщение " + i + " успешно отправлено на " + webhookUrl);
                            success = true;
                            if (i < messageCount) {
                                try {
                                    Thread.sleep(delayMs);
                                } catch (InterruptedException e) {
                                    System.err.println(ANSI_RED + "Прерывание при ожидании задержки: " + e.getMessage());
                                    Thread.currentThread().interrupt();
                                }
                            }
                        } catch (Exception e) {
                            if (e.getMessage().contains("HTTP 429")) {
                                attempt++;
                                try {
                                    String errorBody = e.getMessage().split(" - ", 2)[1];
                                    String retryAfterStr = errorBody.substring(errorBody.indexOf("\"retry_after\":") + 14, errorBody.indexOf(",", errorBody.indexOf("\"retry_after\":")));
                                    long retryAfter = (long) (Double.parseDouble(retryAfterStr) * 1000);
                                    System.out.println(ANSI_RED + "Рейт-лимит на " + webhookUrl + ", ожидание " + retryAfter + " мс (попытка " + attempt + "/" + MAX_RETRIES + ")");
                                    Thread.sleep(retryAfter);
                                } catch (Exception parseEx) {
                                    System.err.println(ANSI_RED + "Ошибка парсинга ответа 429: " + parseEx.getMessage());
                                    break;
                                }
                            } else {
                                System.err.println(ANSI_RED + "Ошибка при отправке сообщения " + i + " на " + webhookUrl + ": " + e.getMessage());
                                break;
                            }
                        }
                    }
                    if (!success) {
                        System.err.println(ANSI_RED + "Не удалось отправить сообщение " + i + " на " + webhookUrl + " после " + MAX_RETRIES + " попыток");
                    }
                }
            }, executor);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println(ANSI_RED + "Ошибка при завершении задач: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        long endTime = System.currentTimeMillis();
        System.out.println(ANSI_BRIGHT_RED + "Время выполнения: " + (endTime - startTime) + " мс");
    }

    private static boolean isValidUrl(String url) {
        return url != null && url.startsWith("https://discord.com/api/webhooks/");
    }

    private static void sendWebhookMessage(String webhookUrl, String content) throws Exception {
        String jsonPayload = "{\"content\":\"" + content.replace("\"", "\\\"") + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(webhookUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204) {
            if (response.statusCode() == 429) {
                throw new Exception("Webhook failed: HTTP 429 - " + response.body());
            } else if (response.statusCode() == 400) {
                System.err.println(ANSI_RED + "Ошибка: Неверный запрос (HTTP 400). Проверьте формат сообщения или URL вебхука.");
                throw new Exception("Webhook failed: HTTP 400 - " + response.body());
            } else if (response.statusCode() == 401 || response.statusCode() == 403) {
                System.err.println(ANSI_RED + "Ошибка: Неверный или неавторизованный вебхук (HTTP " + response.statusCode() + "). Проверьте URL.");
                throw new Exception("Webhook failed: HTTP " + response.statusCode() + " - " + response.body());
            } else {
                throw new Exception("Webhook failed: HTTP " + response.statusCode() + " - " + response.body());
            }
        }
    }
}