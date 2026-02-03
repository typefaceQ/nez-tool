@echo off
chcp 65001 >nul
title Proxy Worker - Автозапуск

REM Настройки
set JAR=crasher-1.0-SNAPSHOT-jar-with-dependencies.jar
set RASPBERRY_IP=192.168.1.100

REM Проверка JAR
if not exist "%JAR%" (
    echo [✗] JAR файл не найден!
    pause
    exit
)

echo [*] Запуск воркера...
echo [*] Подключение к: %RASPBERRY_IP%
echo.

:loop
java -cp "%JAR%" CrAsHeR.proxy.ProxyWorker %RASPBERRY_IP%
echo.
echo [!] Воркер остановлен. Перезапуск через 5 секунд...
timeout /t 5 /nobreak >nul
goto loop
