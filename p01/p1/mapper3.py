#!/usr/bin/env python

import sys
import datetime

class MapperOutput(object):
    def __init__(self):
        pass
       
    def __str__(self):
        return str(vars(self))
        
    def parse_line(self, Line):
        output = True
        try:
            Line = Line.strip()
            data = Line.split(':')
            person = data[0].strip()
            friends = data[1].split(' ')
            print("{}\t{}".format(person, Line))
            for friend in friends:
                friend = friend.strip()
                print("{}\t{}".format(friend, Line))
        except Exception as e:
            # Record exception to stderr
            sys.stderr.write("Error Exception {} :{}".format(str(e), Line))
            output = False
        return output

# End class MapperOutput
        
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
