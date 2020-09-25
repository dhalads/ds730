#!/usr/bin/env python
python_debug="0"
export python_debug
rm intermediate.txt
rm sorted.txt
python ./p1/mapper1.py < orders_test.csv 1> intermediate.tmp 2> errorMapper.tmp
sort -n intermediate.tmp > sorted.tmp
python ./p1/reducer1.py < sorted.tmp 1> output.tmp 2> erroReducer.tmp