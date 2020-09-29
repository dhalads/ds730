#!/usr/bin/env python
 
import sys
import operator
import os

class ReducerOutput(object):

    def __init__(self, Key=None, Count=0):
        """
        docstring
        """
        self._Key = Key
        self._Count = Count

    def __str__(self):
        return str(vars(self))

    def reset(self):
        self._Key = None
        self._Count = 0
        
    def process_line(self, Line):
        try:
            Line = Line.strip()
            countTab = Line.count("\t")
            if(countTab == 0):
                key = ""
                values = Line.strip()
            else:
                key, values = Line.split('\t', 1)

            self.process_key(key)
            self.process_values(values)
        except Exception as e:
            # Record exception to stderr
            sys.stderr.write("Error Exception {} :{}\n".format(str(e), Line))
            sys.stderr.flush()
        
    def process_key(self , Key):
        if(self._Key == None):
            self.set_key(Key)
        elif(self._Key != Key):
            self.process_output()
            self.reset()
            self.set_key(Key)
        else:
            pass

    def process_values(self, Values):
        amount = int(Values)
        self._Count = self._Count + amount

    def process_output(self):
        output = "{}:{}".format(self._Key, self._Count)
        print(str(output))
    
    def set_key(self, Key):
        """
        docstring
        """
        self._Key = Key


reducerOutput = ReducerOutput()
for line in sys.stdin:
    reducerOutput.process_line(line)
reducerOutput.process_output()