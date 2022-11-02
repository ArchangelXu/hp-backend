#!/bin/bash
cd ../ || exit
./mvnw clean install -X
./mvnw spring-boot:run -Dspring-boot.run.profiles=local