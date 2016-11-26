@echo off
java -jar forge_updater.jar 1.11 "%CD%"
gradlew setupDecompWorkspace --refresh-dependencies && gradlew eclipse

