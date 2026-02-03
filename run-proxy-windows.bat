@echo off
chcp 65001 >nul
title Proxy System - Windows 11

echo ╔════════════════════════════════════╗
echo ║   PROXY SYSTEM - WINDOWS 11        ║
echo ╚════════════════════════════════════╝
echo.

set JAR=crasher-1.0-SNAPSHOT-jar-with-dependencies.jar

if not exist "%JAR%" (
    echo [!] JAR файл не найден!
    echo [!] Скопируйте %JAR% в эту папку
    pause
    exit
)

echo Выберите режим:
echo [1] Клиент (Главный ПК)
echo [2] Воркер (ПК Помощник)
echo.
set /p choice="[^>] Выбор: "

if "%choice%"=="1" (
    echo [*] Запуск клиента...
    java -cp "%JAR%" CrAsHeR.proxy.ProxyClient
) else if "%choice%"=="2" (
    set /p ip="[^>] IP Raspberry Pi: "
    echo [*] Запуск воркера...
    java -cp "%JAR%" CrAsHeR.proxy.ProxyWorker %ip%
) else (
    echo [✗] Неверный выбор
    pause
)
