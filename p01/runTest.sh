#!/usr/bin/env python3
python_debug="0"
export python_debug
rm intermediate.txt
rm sorted.txt
python3 ./p1/mapper1.py < orders_test.csv 1> intermediate.txt 2> errorMapper.txt
sort -n intermediate.txt > sorted.txt
python3 ./p1/reducer1.py < sorted.txt 1> output.txt 2> erroReducer.txt