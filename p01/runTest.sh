#!/usr/bin/env python
# testFile="orders_test.csv";problemNum="1"

# testFile="word_vowels.txt";problemNum="2"

testFile="friends.txt";problemNum="3"

python_debug="0"
export python_debug
rm intermediate.txt
rm sorted.txt
python ./p1/mapper$problemNum.py <  $testFile 1> intermediate.tmp 2> errorMapper.tmp
sort -n intermediate.tmp > sorted.tmp
python ./p1/reducer$problemNum.py < sorted.tmp 1> output.tmp 2> erroReducer.tmp