#!/usr/bin/env python

import sys
import datetime

class MapperOutput(object):
    def __init__(self):
        pass
       
    def __str__(self):
        return str(vars(self))
        
    def get_output(self):
        output = ""
        output = "{},{}\t{},{}".format(self._InvoiceDate.strftime("%m"), self._Country, self._CustomerID, self.get_amount())
        return output
        
    def parse_line(self, Line):
        output = True
        try:
            words = Line.split(' ')
            for word in words:
                word = word.strip()
                vowels = self.get_vowels(word)
                output = "{}\t1".format(vowels)
                print(output)
        except Exception as e:
            # Record exception to stderr
            sys.stderr.write("Error Exception {} : {} :: {}".format(str(e), word, line))
            output = False
        return output

    def get_vowels(self, Word):
        vlist = ("a", "e", "i", "o", "u", "y")
        output = ""
        for vowel in vlist:
            count = Word.count(vowel)
            for i in range(0, count):
                output = output + vowel
        if(len(output) == 0):
            output = ""
        return output

# End class LineItem
        
mapper = MapperOutput()
line = sys.stdin.readline()
while line:
  #Do something with line here to create/output
  #as many (key,value) pairs as you want.
  #Do not add anything above this line. The one 
  #exception is that you can add import statements.
    mapper.parse_line(line)


#Do not add anything below this line.
#Read in the next line of the input.
    line = sys.stdin.readline()
