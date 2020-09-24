#!/usr/bin/env python3
rm intermediate.txt
rm sorted.txt
python3 ./p1/mapper1.py < orders_test.csv > intermediate.txt
sort -n intermediate.txt > sorted.txt
python3 ./p1/reducer1.py < sorted.txt > output.txt