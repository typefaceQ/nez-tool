#!/bin/bash

echo "╔════════════════════════════════════╗"
echo "║   NEZ-TOOL PROXY SYSTEM LAUNCHER   ║"
echo "╚════════════════════════════════════╝"
echo

# Сборка проекта
echo "[*] Сборка проекта..."
mvn clean package -q

if [ $? -ne 0 ]; then
    echo "[✗] Ошибка сборки!"
    exit 1
fi

echo "[✓] Проект собран успешно!"
echo

echo "Выберите компонент для запуска:"
echo "[1] ProxyServer (Raspberry Pi)"
echo "[2] ProxyWorker (ПК помощник)"
echo "[3] Function5 с Proxy поддержкой"
echo "[4] ProxyClient (отдельный клиент)"
read -p "[>] Выбор: " choice

case $choice in
    1)
        echo "[*] Запуск ProxyServer..."
        java -cp target/crasher-1.0-SNAPSHOT-jar-with-dependencies.jar CrAsHeR.proxy.ProxyServer
        ;;
    2)
        read -p "[>] IP Raspberry Pi: " ip
        echo "[*] Запуск ProxyWorker..."
        java -cp target/crasher-1.0-SNAPSHOT-jar-with-dependencies.jar CrAsHeR.proxy.ProxyWorker $ip
        ;;
    3)
        echo "[*] Запуск Function5 с Proxy поддержкой..."
        java -cp target/crasher-1.0-SNAPSHOT-jar-with-dependencies.jar CrAsHeR.Function5
        ;;
    4)
        echo "[*] Запуск ProxyClient..."
        java -cp target/crasher-1.0-SNAPSHOT-jar-with-dependencies.jar CrAsHeR.proxy.ProxyClient
        ;;
    *)
        echo "[✗] Неверный выбор!"
        ;;
esac
