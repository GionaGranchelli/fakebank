#!/bin/bash

cd backend/
./mvnw clean package
cd ..
docker-compose build