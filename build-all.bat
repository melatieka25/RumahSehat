@echo off
cd RumahSehatWeb && .\gradlew.bat clean build -x test && cd ..\RumahSehatAPI && .\gradlew.bat clean build -x test && cd ..