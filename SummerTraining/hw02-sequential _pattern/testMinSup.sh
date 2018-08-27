#! /bin/bash

javac test.java

min_sup=0.1;

for(( i=0; i<10; i=i+1 ))
do
    echo "MinSupport: ${min_sup}"
    java test min_sup
    min_sup=$(( echo "scale=10;${min_sup}<<2" | bc ))
done
