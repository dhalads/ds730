#!/bin/bash
# !/usr/bin/env python3

Mapper="../p01/p1/mapper1.py"
Reducer="../p01/p1/reducer1.py"
Input="../p01/orders_test.csv"
Output="./output.tmp"


javac TestStateless.java
java TestStateless $Mapper $Reducer $Input $Output N
