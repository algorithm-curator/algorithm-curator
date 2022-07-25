#!/bin/bash

echo "wait kafka server"
dockerize -wait tcp://kafka:9095 -timeout 20s

echo "start module-api"
java -jar ./module-api.jar