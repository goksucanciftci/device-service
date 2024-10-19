@echo off

REM Navigate to the project directory
cd /d %~dp0

REM Clean, install the project, and run tests with Maven
mvnw.cmd clean install

REM Exit if the build fails
if %errorlevel% neq 0 (
    exit /b 1
)
