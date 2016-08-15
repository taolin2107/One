#!/bin/bash

echo -e "\nmove mapping files\n"
mv ./app/build/outputs/mapping/release/* ./mapping/
mv ./app/build/outputs/apk/AndResGuard_app-release/resource_mapping_app-release.txt ./mapping/

echo -e "\ngradlew clean\n"
./gradlew clean

echo -e "\n\ngradlew resguard\n"
./gradlew resguard
