@echo off

REM Navigate to the project directory
cd /d %~dp0

REM Run the application
if exist "target\device-service-*.jar" (
    java -jar target\device-service-*.jar
) else (
    exit /b 1
)
