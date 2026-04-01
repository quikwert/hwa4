#!/bin/bash
javac -d bin src/main/java/*.java
javac -d bin -cp "lib/*:bin" src/test/java/*.java
