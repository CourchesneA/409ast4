#!/bin/bash

echo "Lenght of string: 350 000" > log.txt
echo "" >> log.txt

for i in 0 1 2 3
do
    echo "" >> log.txt
    echo "Using $i threads:" >> log.txt
    echo "Running 10 tests with $i threads..."
    for j in {1..10}
    do
        echo "Test $i-$j"
        ./regexChecker $i >> log.txt
    done
done
