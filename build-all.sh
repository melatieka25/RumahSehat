#!/bin/bash
cd RumahSehatWeb && chmod +x gradlew && ./gradlew clean build -x test && cd ..\RumahSehatAPI && chmod +x gradlew && ./gradlew clean build -x test && cd ..