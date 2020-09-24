#!/usr/bin/env python

import sys

line = sys.stdin.readline()
while line:
  #Do something with line here to create/output
  #as many (key,value) pairs as you want.
  #Do not add anything above this line. The one
  #exception is that you can add import statements.
  data = line.split()
  curPerson = int(data[0])

  #Obtain value that we want to output with several keys.
  #In other words, output all interests of curPerson.
  index = 1
  value = ""
  while index < len(data):
    value += data[index]+" "
    index = index + 1

  start = curPerson - 2
  while start <= curPerson:
    #obtain keys of curPerson-2:curPerson-1:curPerson and
    #curPerson-1:curPerson:curPerson+1 and
    #curPerson:curPerson+1:curPerson+2
    output = str(start)+":"+str(start+1)+":"+str(start+2)+"\t"
    #append value to the key
    output += value
    print(output)
    start = start + 1

  #Do not add anything below this line.
  #Read in the next line of the input.
  line = sys.stdin.readline()
