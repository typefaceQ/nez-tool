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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Function2 {
    private static final int TIMEOUT = 100; 
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BRIGHT_RED = "\u001B[91m";
    
    public static void execute() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println(ANSI_RED + "═══════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_RED + "           PORT SCANNER v1.0           ");
        System.out.println(ANSI_RED + "═══════════════════════════════════════");
        
        try {
            System.out.print(ANSI_BRIGHT_RED + "Введите хост/IP: ");
            String host = scanner.nextLine().trim();
            
            if (host.isEmpty()) {
                System.out.println("Ошибка: Хост не может быть пустым!");
                return;
            }
            
            System.out.println("\n" + ANSI_RED + "Выберите режим сканирования:");
            System.out.println(ANSI_RED + "1. Быстрое сканирование (популярные порты)");
            System.out.println(ANSI_RED + "2. Диапазон портов");
            System.out.println(ANSI_RED + "3. Конкретные порты");
            System.out.print(ANSI_BRIGHT_RED + "Выбор (1-3): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            List<Integer> portsToScan = new ArrayList<>();
            
            switch (choice) {
                case 1:
                    portsToScan = getCommonPorts();
                    break;
                case 2:
                    portsToScan = getPortRange(scanner);
                    break;
                case 3:
                    portsToScan = getSpecificPorts(scanner);
                    break;
                default:
                    System.out.println("Неверный выбор!");
                    return;
            }
            
            if (portsToScan.isEmpty()) {
                System.out.println("Нет портов для сканирования!");
                return;
            }
            
            scanPorts(host, portsToScan);
            
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private static List<Integer> getCommonPorts() {
        List<Integer> ports = new ArrayList<>();
        int[] commonPorts = {21, 22, 23, 25, 53, 80, 110, 135, 139, 143, 443, 
                           993, 995, 1723, 3306, 3389, 5432, 5900, 8080, 8443};
        for (int port : commonPorts) {
            ports.add(port);
        }
        return ports;
    }
    
    private static List<Integer> getPortRange(Scanner scanner) {
        List<Integer> ports = new ArrayList<>();
        try {
            System.out.print(ANSI_BRIGHT_RED + "Начальный порт: ");
            int startPort = scanner.nextInt();
            System.out.print(ANSI_BRIGHT_RED + "Конечный порт: ");
            int endPort = scanner.nextInt();
            
            if (startPort < 1 || endPort > 65535 || startPort > endPort) {
                System.out.println("Неверный диапазон портов!");
                return ports;
            }
            
            if (endPort - startPort > 1000) {
                System.out.println(ANSI_RED + "Большой диапазон, сканирование может занять много времени...");
            }
            
            for (int i = startPort; i <= endPort; i++) {
                ports.add(i);
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода!");
        }
        return ports;
    }
    
    private static List<Integer> getSpecificPorts(Scanner scanner) {
        List<Integer> ports = new ArrayList<>();
        System.out.print(ANSI_BRIGHT_RED + "Введите порты через запятую (например: 80,443,8080): ");
        String input = scanner.nextLine();
        
        try {
            String[] portStrings = input.split(",");
            for (String portStr : portStrings) {
                int port = Integer.parseInt(portStr.trim());
                if (port >= 1 && port <= 65535) {
                    ports.add(port);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат портов!");
        }
        return ports;
    }
    
    private static void scanPorts(String host, List<Integer> ports) {
        System.out.println("\n" + ANSI_RED + "Начинаем сканирование " + host + "...");
        System.out.println(ANSI_RED + "Портов для сканирования: " + ports.size());
        System.out.println(ANSI_RED + "Таймаут: " + TIMEOUT + "ms");
        System.out.println();
        
        ExecutorService executor = Executors.newFixedThreadPool(50);
        List<Integer> openPorts = new ArrayList<>();
        
        long startTime = System.currentTimeMillis();
        
        for (int port : ports) {
            executor.submit(() -> {
                if (isPortOpen(host, port)) {
                    synchronized (openPorts) {
                        openPorts.add(port);
                        String service = getServiceName(port);
                        System.out.println(ANSI_BRIGHT_RED + "✓ Порт " + port + " ОТКРЫТ" + 
                                         (service.isEmpty() ? "" : " [" + service + "]"));
                    }
                } else {
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Сканирование прервано!");
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("\n" + ANSI_RED + "═══════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_RED + "              РЕЗУЛЬТАТЫ                ");
        System.out.println(ANSI_RED + "═══════════════════════════════════════");
        
        if (openPorts.isEmpty()) {
            System.out.println("Открытые порты не найдены.");
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Найдено открытых портов: " + openPorts.size());
            openPorts.sort(Integer::compareTo);
            
            System.out.println("\n" + ANSI_RED + "Открытые порты:");
            for (int port : openPorts) {
                String service = getServiceName(port);
                System.out.println(ANSI_BRIGHT_RED + "  " + port + 
                                 (service.isEmpty() ? "" : " - " + service));
            }
        }
        
        System.out.println("\n" + ANSI_RED + "Время сканирования: " + duration + "ms");
        System.out.println(ANSI_RED + "Хост: " + host);
        System.out.println(ANSI_RED + "Просканировано портов: " + ports.size());
    }
    
    private static boolean isPortOpen(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), TIMEOUT);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    private static String getServiceName(int port) {
        System.out.println(ANSI_RED + "");
        switch (port) {
            case 21: return "FTP";
            case 22: return "SSH";
            case 23: return "Telnet";
            case 25: return "SMTP";
            case 53: return "DNS";
            case 80: return "HTTP";
            case 110: return "POP3";
            case 135: return "RPC";
            case 139: return "NetBIOS";
            case 143: return "IMAP";
            case 443: return "HTTPS";
            case 993: return "IMAPS";
            case 995: return "POP3S";
            case 1723: return "PPTP";
            case 3306: return "MySQL";
            case 3389: return "RDP";
            case 5432: return "PostgreSQL";
            case 5900: return "VNC";
            case 8080: return "HTTP-Alt";
            case 8443: return "HTTPS-Alt";
            default: return "";
        }
    }
}