#!/bin/bash

echo "╔════════════════════════════════════╗"
echo "║   PROXY SYSTEM - БЫСТРЫЙ ЗАПУСК    ║"
echo "╚════════════════════════════════════╝"
echo ""

JAR="target/crasher-1.0-SNAPSHOT-jar-with-dependencies.jar"

if [ ! -f "$JAR" ]; then
    echo "[!] JAR файл не найден. Сборка..."
    mvn clean package -DskipTests
fi

echo ""
echo "Выберите режим:"
echo "[1] Сервер (Raspberry Pi)"
echo "[2] Клиент (Главный ПК)"
echo "[3] Воркер (ПК Помощник)"
echo ""
read -p "[>] Выбор: " choice

case $choice in
    1)
        echo "[*] Запуск сервера..."
        java -cp "$JAR" CrAsHeR.proxy.ProxyServer
        ;;
    2)
        echo "[*] Запуск клиента..."
        java -cp "$JAR" CrAsHeR.proxy.ProxyClient
        ;;
    3)
        read -p "[>] IP Raspberry Pi: " ip
        echo "[*] Запуск воркера..."
        java -cp "$JAR" CrAsHeR.proxy.ProxyWorker "$ip"
        ;;
    *)
        echo "[✗] Неверный выбор"
        ;;
esac
