#!/usr/bin/env python
#Be sure the indentation is correct and also be sure the line above this is on the first line
 
import sys
 
current_key = None
current_dictionary = {}
key = None  

for line in sys.stdin:
  line = line.strip()
  key, value = line.split('\t', 1)
  if current_key == key:
    #if the current key read in is the same one I've been reading, update dictionary
    if value in current_dictionary:
      #the 2 character string is already in the dictionary, update it's total
      current_dictionary[value] = current_dictionary[value] + 1
    else:
      #the 2 character string is new, add to dictionary
      current_dictionary[value] = 1
  else:
    #either the start or a brand new key was obtained
    if current_key:
      #if there was a key being considered, find the largest 2 character combo and print it out
      largest = 0
      biggestCombo = ""
      for charCombo, total in current_dictionary.items():
        if total > largest:
          largest = total
          biggestCombo = charCombo
      print('%s\t%s' % (current_key, biggestCombo))
    #since this is a new key, clear the dictionary and change the current key's value
    current_dictionary = {}
    current_dictionary[value] = 1
    current_key = key

#code to take care of the last key
if current_key == key:
  largest = 0
  biggestCombo = ""
  for charCombo, total in current_dictionary.items():
    if total > largest:
      largest = total
      biggestCombo = charCombo
  print('%s\t%s' % (current_key, biggestCombo))
