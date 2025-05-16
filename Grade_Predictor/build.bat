@echo off
echo Building and running Grade Predictor...

call mvn clean compile

echo Packaging the application...
call mvn package

echo Starting the application...
call mvn javafx:run

echo Done!