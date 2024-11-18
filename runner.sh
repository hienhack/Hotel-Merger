#!/bin/bash

JAR_FILE="target/hotel-merger-1.0.jar"

# Check if the JAR file exists
if [ ! -f "$JAR_FILE" ]; then
  mvn clean package
fi

# Run the project
java -jar "$JAR_FILE" "$1" "$2"