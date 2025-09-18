#!/bin/bash
for i in {1..30}; do
    echo "Run $i"
    make run ARGS="HIGH CLH 16 2"
done
