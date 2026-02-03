package CrAsHeR.proxy;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.*;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;

public class ProxyServer {
    private static final int PORT = 8080;
    private static final Queue<String> commandQueue = new ConcurrentLinkedQueue<>();
    private static final Map<String, String> results = new ConcurrentHashMap<>();
    
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        server.createContext("/command", new CommandHandler());
        server.createContext("/get", new GetCommandHandler());
        server.createContext("/result", new ResultHandler());
        server.createContext("/status", new StatusHandler());
        
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
        
        System.out.println("[✓] Proxy Server запущен на порту " + PORT);
    }
    
    static class CommandHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String command = new String(exchange.getRequestBody().readAllBytes());
                commandQueue.offer(command);
                
                String response = "OK";
                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
                
                System.out.println("[+] Команда получена: " + command);
            }
        }
    }
    
    static class GetCommandHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String command = commandQueue.poll();
            String response = command != null ? command : "NONE";
            
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
    }
    
    static class ResultHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String result = new String(exchange.getRequestBody().readAllBytes());
                String workerId = exchange.getRequestHeaders().getFirst("Worker-ID");
                results.put(workerId, result);
                
                String response = "OK";
                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
                
                System.out.println("[✓] Результат от " + workerId + ": " + result);
            }
        }
    }
    
    static class StatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String status = "Queue: " + commandQueue.size() + " | Results: " + results.size();
            exchange.sendResponseHeaders(200, status.length());
            exchange.getResponseBody().write(status.getBytes());
            exchange.close();
        }
    }
}
