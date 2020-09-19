#!/usr/bin/env python

import sys
import re

line = sys.stdin.readline()
pattern = re.compile("[a-zA-Z0-9]+")
while line:
    for word in pattern.findall(line):
        print(word+"\t"+"1")
    line = sys.stdin.readline()
